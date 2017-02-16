/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sp.telesul.service;

import br.sp.telesul.controller.CrudController;
import br.sp.telesul.model.Certificacao;
import br.sp.telesul.model.Formacao;
import br.sp.telesul.model.Funcionario;
import br.sp.telesul.model.Idioma;
import br.sp.telesul.model.Language;
import br.sp.telesul.model.Nivel;
import br.sp.telesul.model.Treinamento;
import br.sp.telesul.repository.FuncionarioRepository;
import com.mysql.jdbc.StringUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jdk.nashorn.internal.runtime.Undefined;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author ebranco
 */
@Service
public class ExportServiceImpl implements ExportService {

    @Autowired
    private CrudService<Funcionario> crudService;

    @Autowired
    private FuncionarioRepository funcRepository;

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(ExportServiceImpl.class);

    @Override
    public void buildExcelDocument(String type, String[] columns, String[] columnsFormacao, String[] columnsIdiomas, String[] columnsCertificacoes, String habilitado, HttpServletRequest request, HttpServletResponse response) {
        List<Funcionario> funcionarios = crudService.findAll();
        Collections.sort(funcionarios, new Comparator<Funcionario>() {
            @Override
            public int compare(Funcionario func1, Funcionario func2) {
                return func1.getNome().compareTo(func2.getNome());
            }
        });
        HSSFWorkbook workbook = new HSSFWorkbook();
        writeExcel("Funcionarios", columns, workbook, funcionarios, habilitado);
        writeExcelFormacao("Formações", columnsFormacao, workbook, funcionarios, habilitado);
        writeExcelIdiomas("Idiomas", columnsIdiomas, workbook, funcionarios, habilitado);
        writeExcelCertificacoes("Certificações", columnsCertificacoes, workbook, funcionarios, habilitado);
        writeExcelTreinamentos("Treinamentos", workbook, funcionarios, habilitado);
        downloadExcel(request, response, workbook);
    }

    public void writeExcel(String templateHead, String[] columns, HSSFWorkbook workbook, List<Funcionario> funcionarios, String habilitado) {
        try {

            HSSFSheet sheet = workbook.createSheet(templateHead);

            Row rowHeading = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                rowHeading.createCell(i).setCellValue(columns[i]);
            }
            stylizeHeader(rowHeading, workbook, columns);

            CellStyle styleDate = workbook.createCellStyle();
            HSSFDataFormat dfAdmissao = workbook.createDataFormat();
            styleDate.setDataFormat(dfAdmissao.getFormat("dd/mm/yyyy"));
            int r = 1;
            for (Funcionario f : funcionarios) {
                if (checkHabilitado(f.getHabilitado()) == checkTypeHabilitado(habilitado)) {
                    Row row = sheet.createRow(r);

                    Cell Nome = row.createCell(0);

                    assigncellValue(Nome, f.getNome());
                    Cell cargo = row.createCell(1);

                    assigncellValue(cargo, f.getCargo());
                    Cell dtAdmissao = row.createCell(2);

                    dtAdmissao.setCellStyle(styleDate);
                    assigncellValue(dtAdmissao, f.getDtAdmissao());

                    Cell area = row.createCell(3);

                    assigncellValue(area, f.getArea());

                    Cell gestor = row.createCell(4);

                    assigncellValue(gestor, f.getGestor());
                    Cell email = row.createCell(5);

                    assigncellValue(email, f.getEmail());

                    if (isNull(f.getTelefone())) {
                        if (f.getTelefone().length() > 6) {
                            Cell telefone = row.createCell(6);

                            assigncellValue(telefone, f.getTelefone());
                        }
                    }

                    Cell celular = row.createCell(7);

                    assigncellValue(celular, f.getCelular());

                    Cell dtSaida = row.createCell(8);
                    assigncellValue(dtSaida, f.getDtSaida());
                    styleDate.setDataFormat(dfAdmissao.getFormat("dd/mm/yyyy"));
                    dtSaida.setCellStyle(styleDate);

                    r++;
                }
            }

            autoSizeColum(sheet, columns);

        } catch (Exception e) {
            logger.error("Error gerate Report: " + e);
        }
    }

    private boolean checkHabilitado(Boolean habilitado) {

        if (habilitado == null || habilitado == true) {
            return true;
        } else {
            return false;
        }

    }

    private boolean checkTypeHabilitado(String habilitado) {
        if (habilitado.equalsIgnoreCase("true")) {
            return true;
        } else {
            return false;
        }
    }

    public void writeExcelFormacao(String templateHead, String[] columns, HSSFWorkbook workbook, List<Funcionario> funcionarios, String habilitado) {
        try {

            HSSFSheet sheet = workbook.createSheet(templateHead);

            Row rowHeading = sheet.createRow(0);
            writeHeaderColumns(rowHeading, columns);
            //Estilizar o Cabeçalho - Stylesheet the heading
            stylizeHeader(rowHeading, workbook, columns);
            //Preencher linhas
            int r = 1;
            for (Funcionario f : funcionarios) {
                if (checkHabilitado(f.getHabilitado()) == checkTypeHabilitado(habilitado)) {
                    if (!f.getFormacoes().isEmpty()) {

                        for (Formacao fmc : f.getFormacoes()) {
                            if (!fmc.getInstituicao().isEmpty() || !fmc.getCurso().isEmpty() || !fmc.getNivel().isEmpty()) {
                                Row row = sheet.createRow(r);

                                Cell Nome = row.createCell(0);

                                assigncellValue(Nome, f.getNome());

                                Cell area = row.createCell(1);

                                assigncellValue(area, f.getArea());

                                Cell curso = row.createCell(2);

                                assigncellValue(curso, fmc.getCurso());

                                Cell instituicao = row.createCell(3);

                                assigncellValue(instituicao, fmc.getInstituicao());

                                Cell nivel = row.createCell(4);

                                assigncellValue(nivel, fmc.getNivel());

                                Cell copia = row.createCell(5);

                                assigncellValue(copia, fmc.getCopiaCertificado());

                                r++;
                            }
                        }

                    }
                }

            }
            autoSizeColum(sheet, columns);

        } catch (Exception e) {
            logger.error("Error" + " " + e);
        }
    }

    public void writeExcelCertificacoes(String templateHead, String[] columns, HSSFWorkbook workbook, List<Funcionario> funcionarios, String habilitado) {
        try {
            HSSFSheet sheet = workbook.createSheet(templateHead);

            Row rowHeading = sheet.createRow(0);
            writeHeaderColumns(rowHeading, columns);
            //Estilizar o Cabeçalho - Stylesheet the heading
            stylizeHeader(rowHeading, workbook, columns);
            //Preencher linhas
            int r = 1;
            for (Funcionario f : funcionarios) {
                if (checkHabilitado(f.getHabilitado()) == checkTypeHabilitado(habilitado)) {
                    if (!f.getCertificacoes().isEmpty()) {

                        for (Certificacao ct : f.getCertificacoes()) {
                            Row row = sheet.createRow(r);

                            CellStyle styleDate = workbook.createCellStyle();
                            HSSFDataFormat dfExame = workbook.createDataFormat();
                            styleDate.setDataFormat(dfExame.getFormat("dd/mm/yyyy"));

                            Cell Nome = row.createCell(0);

                            assigncellValue(Nome, f.getNome());

                            Cell area = row.createCell(1);
                            assigncellValue(area, f.getArea());

                            Cell cod = row.createCell(2);

                            assigncellValue(cod, ct.getCodigo());

                            Cell nome = row.createCell(3);

                            assigncellValue(nome, ct.getNome());

                            Cell empresa = row.createCell(4);

                            assigncellValue(empresa, ct.getEmpresa());

                            Cell dtExame = row.createCell(5);

                            assigncellValue(dtExame, ct.getDtExame());
                            dtExame.setCellStyle(styleDate);

                            Cell dtValidade = row.createCell(6);

                            assigncellValue(dtValidade, ct.getDtValidade());
                            dtValidade.setCellStyle(styleDate);

                            Cell copia = row.createCell(7);
                            assigncellValue(copia, ct.getCopia());
                            copia.setCellValue(ct.getCopia());

                            r++;
                        }

                    }
                }

            }

            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }
//            String file = "C:/Users/ebranco.TELESULCORP/new.xls";
//            FileOutputStream out = new FileOutputStream(file);
//            workbook.write(out);
//            out.close();
//            workbook.close();
//            System.out.println("Excell write succesfully");
        } catch (Exception e) {
            logger.error("Error" + " " + e);
        }
    }

    public void writeExcelTreinamentos(String templateHead, HSSFWorkbook workbook, List<Funcionario> funcionarios, String habilitado) {
        String[] columns = {"Nome", "Área", "Codigo", "Certificadora", "Treinamento", "Versão", "Data de Início", "Data de Término"};
        try {
            HSSFSheet sheet = workbook.createSheet(templateHead);

            Row rowHeading = sheet.createRow(0);
            writeHeaderColumns(rowHeading, columns);
            //Estilizar o Cabeçalho - Stylesheet the heading
            stylizeHeader(rowHeading, workbook, columns);
            //Preencher linhas
            int r = 1;
            for (Funcionario f : funcionarios) {
                if (checkHabilitado(f.getHabilitado()) == checkTypeHabilitado(habilitado)) {
                    if (!f.getTreinamentos().isEmpty()) {

                        for (Treinamento tr : f.getTreinamentos()) {
                            Row row = sheet.createRow(r);

                            CellStyle styleDt = workbook.createCellStyle();
                            HSSFDataFormat dtf = workbook.createDataFormat();
                            styleDt.setDataFormat(dtf.getFormat("mm/yyyy"));

                            Cell Nome = row.createCell(0);
                            assigncellValue(Nome, f.getNome());

                            Cell area = row.createCell(1);
                            assigncellValue(area, f.getArea());

                            Cell cod = row.createCell(2);
                            assigncellValue(cod, tr.getCodigo());

                            Cell certificadora = row.createCell(3);
                            assigncellValue(certificadora, tr.getCertificadora());

                            Cell treinamento = row.createCell(4);
                            assigncellValue(treinamento, tr.getTreinamento());

                            Cell versao = row.createCell(5);
                            assigncellValue(versao, tr.getVersao());

                            Cell dtInicio = row.createCell(6);
                            assigncellValue(dtInicio, tr.getDtInicio());
                            dtInicio.setCellStyle(styleDt);

                            Cell dtTermino = row.createCell(7);
                            assigncellValue(dtTermino, tr.getDtTermino());
                            dtTermino.setCellStyle(styleDt);

                            r++;
                        }

                    }
                }

            }
            autoSizeColum(sheet, columns);
        } catch (Exception e) {
            logger.error("Error in Write Excel Treinamentos" + e);
        }
    }

    public void writeExcelIdiomas(String templateHead, String[] columns, HSSFWorkbook workbook, List<Funcionario> funcionarios, String habilitado) {
        try {
            HSSFSheet sheet = workbook.createSheet(templateHead);

            Row rowHeading = sheet.createRow(0);
            writeHeaderColumns(rowHeading, columns);
            //Estilizar o Cabeçalho - Stylesheet the heading
            stylizeHeader(rowHeading, workbook, columns);
            //Preencher linhas
            int r = 1;
            for (Funcionario f : funcionarios) {
                if (checkHabilitado(f.getHabilitado()) == checkTypeHabilitado(habilitado)) {
                    if (!f.getIdiomas().isEmpty()) {

                        for (Idioma idm : f.getIdiomas()) {
                            try {
                                if (idm.getNivel() != null || idm.getNome() != null) {
                                    Row row = sheet.createRow(r);
                                    Cell Nome = row.createCell(0);
                                    assigncellValue(Nome, f.getNome());
                                    
                                    Cell area = row.createCell(1);
                                    assigncellValue(area, f.getArea());

                                    Cell language = row.createCell(2);
                                    assigncellValue(language, idm.getNome().toString());
                                    
                                    Cell nivel = row.createCell(3);
                                    assigncellValue(nivel, idm.getNivel().toString());
                                    r++;
                                }
                            } catch (NullPointerException ne) {
                                logger.error("Error" + " " + ne);
                                break;
                            }

                        }

                    }
                }

            }
            autoSizeColum(sheet, columns);

        } catch (Exception e) {
            logger.error("Error" + " " + e);
        }
    }

    public void downloadExcel(HttpServletRequest request, HttpServletResponse response, HSSFWorkbook wb) {
        ServletOutputStream stream = null;
        String fileName = "relatorio" + " " + new Date().getTime();
        fileName = fileName.replace(" ", "_");
        try {
            stream = response.getOutputStream();
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xls");
            response.setContentType("application/vnd.ms-excel");
            wb.write(stream);

        } catch (Exception e) {
            logger.error("Error write excel" + " " + e);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                    wb.close();
                } catch (IOException io) {
                    logger.error("Error close Stream" + " " + io);
                }

            }
        }

    }

    @Override
    public List<Funcionario> readExcelDocument() {
        try {
            List<Funcionario> funcionariosExcel = new ArrayList<>();
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

            Workbook wb = new XSSFWorkbook(classLoader.getResourceAsStream("Matriz1.xlsx"));
            Sheet firstSheet = wb.getSheetAt(0);
            Iterator<Row> iterator = firstSheet.iterator();

            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                int row = nextRow.getRowNum();

                Iterator<Cell> cellIterator = nextRow.cellIterator();
                Funcionario f = new Funcionario();
                Formacao fm = new Formacao();
                Idioma id = new Idioma();
                int column = 0;
                while (cellIterator.hasNext()) {
                    Cell nextCell = cellIterator.next();
                    int columnIndex = nextCell.getColumnIndex();
                    column = columnIndex;

                    if (row > 0) {
                        switch (columnIndex) {
                            case 1:
                                f.setArea((String) getCellValue(nextCell));
                                break;
                            case 2:
                                Date dt = new Date();
                                if (!getCellValue(nextCell).toString().isEmpty()) {
                                    try {
                                        dt = DateUtil.getJavaDate((Double) getCellValue(nextCell));
                                    } catch (ClassCastException cce) {
                                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");

                                        dt = formatter.parse((String) getCellValue((nextCell)));
                                    };
                                }

                                f.setDtAdmissao(dt);
                                break;
                            case 3:
                                f.setCargo((String) getCellValue(nextCell));
                                break;
                            case 4:
                                f.setNome((String) getCellValue(nextCell));
                                break;
                            case 5:
                                f.setGestor((String) getCellValue(nextCell));
                                break;
                            case 9:
                                fm.setNivel((String) getCellValue(nextCell));
                                break;
                            case 10:
                                fm.setCurso((String) getCellValue(nextCell));
                                break;
                            case 11:
                                fm.setInstituicaoo((String) getCellValue(nextCell));
                                break;
                            case 12:
                                String typeEnum = (String) getCellValue(nextCell);
                                if (!typeEnum.isEmpty()) {
                                    id.setNome(Language.valueOf(typeEnum.trim()));
                                }

                                break;
                            case 13:
                                String typeEnumNivel = (String) getCellValue(nextCell);
                                if (!typeEnumNivel.isEmpty()) {
                                    id.setNivel(Nivel.valueOf(typeEnumNivel.trim()));
                                }

                                break;
                        }
                    }

                }

                List<Formacao> listFm = new ArrayList<>();
                listFm.add(fm);
                f.setFormacoes(listFm);

                List<Idioma> listId = new ArrayList<>();
                listId.add(id);
                f.setIdiomas(listId);

                if (row > 0) {
                    funcionariosExcel.add(f);
                }

            }
            wb.close();

            return funcionariosExcel;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private Object getCellValue(Cell cell) {
        Object o = "";
        try {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    o = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    o = cell.getBooleanCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    o = cell.getNumericCellValue();
                    break;
                case Cell.CELL_TYPE_BLANK:
                    o = "";
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error" + " " + e);
        }
        return o;
    }

    @Override
    public void singleReport(Long id, HttpServletRequest request, HttpServletResponse response) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        writeExcelSingle(id, workbook);
        downloadExcel(request, response, workbook);
    }

    public void writeExcelSingle(Long id, HSSFWorkbook workbook) {
        String[] columns = {"Nome", "Cargo", "Data de Admissao", "Área", "Gestor", "Email", "Telefone", "Celular", "Data de Saída"};
        String[] colFormacao = {"Formação", "Curso", "Instituição", "Cópia de Certificação"};
        String[] colIdioma = {"Idioma", "Nível"};
        String[] colCertificacao = {"Certificadora", "Exame", "Código", "Data de Exame", "Data de Validade", "Cópia de Certificado"};
        String[] columnsTrainne = {"Codigo", "Certificadora", "Treinamento", "Versão", "Data de Início", "Data de Término"};
        try {
            //        Funcionario funcionario = this.crudService.findOne(id);
            Funcionario funcionario = this.funcRepository.findFuncionarioById(id);

            HSSFSheet sheet = workbook.createSheet("Funcionário");
            writeSingleDadosCadastrais(columns, workbook, sheet, funcionario);

            //formação
            int auxRow = 12;
            int headerFormacao = 11;
            if (funcionario.getFormacoes() != null) {
                auxRow = writeSingleFormacao(colFormacao, workbook, sheet, funcionario, headerFormacao);
            }

            if (funcionario.getIdiomas() != null) {
                int headerIdiomas = auxRow + 9;
                auxRow = writeSingleExcelIdiomas(colIdioma, workbook, sheet, funcionario, headerIdiomas);
            }

            if (funcionario.getCertificacoes().size() > 0) {
                int headerCertificacao = auxRow + 9;

                CellStyle styleDate = workbook.createCellStyle();
                HSSFDataFormat df = workbook.createDataFormat();
                styleDate.setDataFormat(df.getFormat("dd/mm/yyyy"));
                auxRow = writeSinglesCertificacao(colCertificacao, workbook, sheet, funcionario, headerCertificacao, styleDate);

            }
            CellStyle styleDt = workbook.createCellStyle();
            HSSFDataFormat dtf = workbook.createDataFormat();
            styleDt.setDataFormat(dtf.getFormat("mm/yyyy"));
            if (!funcionario.getTreinamentos().isEmpty()) {
                int headerTreinamento = auxRow + 9;
                writeSingleTreinamento(columnsTrainne, workbook, sheet, funcionario, headerTreinamento, styleDt);
            }

        } catch (Exception e) {
            logger.error("Error Writing Single Report: " + e);
        }
    }

    private void assigncellValue(Cell cell, Object obj) {
        if (obj != null) {
            if (obj instanceof Date) {
                Date val = (Date) obj;
                cell.setCellValue(val);
            } else if (obj instanceof String) {
                String val = (String) obj;
                cell.setCellValue(val);
            } else if (obj instanceof Integer) {
                Integer val = (Integer) obj;
                cell.setCellValue(val);
            }
        }
    }

    private void writeHeaderColumns(Row rowHeading, String[] columns) {
        for (int i = 0; i < columns.length; i++) {
            rowHeading.createCell(i).setCellValue(columns[i]);
        }
    }

    ;
    private void stylizeHeader(Row rowHeading, HSSFWorkbook workbook, String[] columns) {
        for (int i = 0; i < columns.length; i++) {
            CellStyle stylerowHeading = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            font.setFontName(HSSFFont.FONT_ARIAL);
            font.setFontHeightInPoints((short) 11);
            font.setColor(HSSFColor.WHITE.index);
            stylerowHeading.setFont(font);
            stylerowHeading.setVerticalAlignment(CellStyle.ALIGN_CENTER);
            stylerowHeading.setFillForegroundColor(HSSFColor.ROYAL_BLUE.index);
            stylerowHeading.setFillPattern(CellStyle.SOLID_FOREGROUND);
            rowHeading.getCell(i).setCellStyle(stylerowHeading);
        }
    }

    private void autoSizeColum(HSSFSheet sheet, String[] columns) {
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    public boolean isNull(String o) {
        if (o != null) {
            return true;
        }
        return false;
    }

    private void writeSingleDadosCadastrais(String[] columns, HSSFWorkbook workbook, HSSFSheet sheet, Funcionario funcionario) {
        Row rowHeading = sheet.createRow(0);
        writeHeaderColumns(rowHeading, columns);
        stylizeHeader(rowHeading, workbook, columns);
        int r = 1;

        Row row = sheet.createRow(r);

        Cell Nome = row.createCell(0);
        Nome.setCellValue(funcionario.getNome());
        assigncellValue(Nome, funcionario.getNome());
        Cell cargo = row.createCell(1);
        cargo.setCellValue(funcionario.getCargo());
        assigncellValue(cargo, funcionario.getCargo());

        Cell dtAdmissao = row.createCell(2);
        dtAdmissao.setCellValue(funcionario.getDtAdmissao());
        assigncellValue(dtAdmissao, funcionario.getDtAdmissao());

        CellStyle styleDate = workbook.createCellStyle();
        HSSFDataFormat dfAdmissao = workbook.createDataFormat();
        styleDate.setDataFormat(dfAdmissao.getFormat("dd/mm/yyyy"));
        dtAdmissao.setCellStyle(styleDate);

        Cell area = row.createCell(3);
        area.setCellValue(funcionario.getArea());
        assigncellValue(area, funcionario.getArea());

        Cell gestor = row.createCell(4);
        gestor.setCellValue(funcionario.getGestor());
        assigncellValue(gestor, funcionario.getGestor());

        Cell email = row.createCell(5);
        email.setCellValue(funcionario.getEmail());
        assigncellValue(email, funcionario.getEmail());

        if (isNull(funcionario.getTelefone())) {
            if (funcionario.getTelefone().length() > 5) {
                Cell telefone = row.createCell(6);
                telefone.setCellValue(funcionario.getTelefone());
                assigncellValue(telefone, funcionario.getTelefone());
            }
        }

        Cell celular = row.createCell(7);
        celular.setCellValue(funcionario.getCelular());
        assigncellValue(celular, funcionario.getCelular());

        Cell dtSaida = row.createCell(8);
        dtSaida.setCellStyle(styleDate);
        assigncellValue(dtSaida, funcionario.getDtSaida());

    }

    private int writeSingleFormacao(String[] colFormacao, HSSFWorkbook workbook, HSSFSheet sheet, Funcionario funcionario, int headerFormacao) {
        Row rowHeadingForm = sheet.createRow(headerFormacao);
        writeHeaderColumns(rowHeadingForm, colFormacao);
        stylizeHeader(rowHeadingForm, workbook, colFormacao);
        int rowFormacao = headerFormacao + 1;
        for (Formacao form : funcionario.getFormacoes()) {
            if (!form.getCurso().isEmpty() || !form.getNivel().isEmpty() || !form.getInstituicao().isEmpty()) {

                Row rowFormacaoDatas = sheet.createRow(rowFormacao);
                Cell formacao = rowFormacaoDatas.createCell(0);
                Cell curso = rowFormacaoDatas.createCell(1);
                Cell instituicao = rowFormacaoDatas.createCell(2);
                Cell copy = rowFormacaoDatas.createCell(3);

                assigncellValue(formacao, form.getNivel());

                curso.setCellValue(form.getCurso());
                assigncellValue(curso, form.getCurso());

                instituicao.setCellValue(form.getInstituicao());
                assigncellValue(instituicao, form.getInstituicao());

                copy.setCellValue(form.getCopiaCertificado());
                assigncellValue(copy, form.getCopiaCertificado());

                rowFormacao++;
                autoSizeColum(sheet, colFormacao);
            }
        }
        return rowFormacao;
    }

    private int writeSingleExcelIdiomas(String[] columns, HSSFWorkbook workbook, HSSFSheet sheet, Funcionario f, int headerIdiomas) {
        try {
            Row rowHeading = sheet.createRow(headerIdiomas);
            writeHeaderColumns(rowHeading, columns);
            stylizeHeader(rowHeading, workbook, columns);
            //Preencher linhas
            int r = headerIdiomas + 1;

            if (!f.getIdiomas().isEmpty()) {

                for (Idioma idm : f.getIdiomas()) {
                    if (idm.getNivel() != null || idm.getNome() != null) {
                        Row row = sheet.createRow(r);

                        Cell language = row.createCell(0);

                        assigncellValue(language, idm.getNome().toString());
                        Cell nivel = row.createCell(1);

                        assigncellValue(nivel, idm.getNivel().toString());
                        r++;
                    }
                }
            }
            autoSizeColum(sheet, columns);
            return r;
        } catch (Exception e) {
            logger.error("Error" + " " + e);
        }
        return 0;
    }

    private int writeSinglesCertificacao(String[] colCertificacao, HSSFWorkbook workbook, HSSFSheet sheet, Funcionario funcionario, int headerCertificacao, CellStyle styleDate) {
        Row rowHeadingCert = sheet.createRow(headerCertificacao);
        writeHeaderColumns(rowHeadingCert, colCertificacao);
        stylizeHeader(rowHeadingCert, workbook, colCertificacao);
        int rowCert = headerCertificacao + 1;

        for (Certificacao c : funcionario.getCertificacoes()) {
            Row rowCertDatas = sheet.createRow(rowCert);
            Cell certificadora = rowCertDatas.createCell(0);
            Cell exame = rowCertDatas.createCell(1);
            Cell codigo = rowCertDatas.createCell(2);
            Cell dtExame = rowCertDatas.createCell(3);
            dtExame.setCellStyle(styleDate);
            Cell dtValidade = rowCertDatas.createCell(4);
            dtValidade.setCellStyle(styleDate);
            Cell copia = rowCertDatas.createCell(5);

            certificadora.setCellValue(c.getEmpresa());
            assigncellValue(certificadora, c.getEmpresa());

            exame.setCellValue(c.getNome());
            assigncellValue(exame, c.getNome());

            codigo.setCellValue(c.getCodigo());
            assigncellValue(codigo, c.getCodigo());

            dtExame.setCellValue(c.getDtExame());
            assigncellValue(dtExame, c.getDtExame());

            dtValidade.setCellValue(c.getDtValidade());
            assigncellValue(dtValidade, c.getDtValidade());

            copia.setCellValue(c.getCopia());
            assigncellValue(copia, c.getCopia());

            rowCert++;
        }
        autoSizeColum(sheet, colCertificacao);
        return rowCert;
    }

    private void writeSingleTreinamento(String[] columnsTrainne, HSSFWorkbook workbook, HSSFSheet sheet, Funcionario funcionario, int headerTreinamento, CellStyle styleDt) {
        Row rowHeadingTreinamento = sheet.createRow(headerTreinamento);
        writeHeaderColumns(rowHeadingTreinamento, columnsTrainne);
        stylizeHeader(rowHeadingTreinamento, workbook, columnsTrainne);
        int rowTrainne = headerTreinamento + 1;

        for (Treinamento trainne : funcionario.getTreinamentos()) {
            Row rowTrainneDatas = sheet.createRow(rowTrainne);
            Cell codigo = rowTrainneDatas.createCell(0);
            Cell certificadora = rowTrainneDatas.createCell(1);
            Cell treinamento = rowTrainneDatas.createCell(2);
            Cell versao = rowTrainneDatas.createCell(3);
            Cell dtInicio = rowTrainneDatas.createCell(4);
            dtInicio.setCellStyle(styleDt);
            Cell dtTermino = rowTrainneDatas.createCell(5);
            dtTermino.setCellStyle(styleDt);

            assigncellValue(codigo, trainne.getCodigo());
            assigncellValue(certificadora, trainne.getCertificadora());
            assigncellValue(treinamento, trainne.getTreinamento());
            assigncellValue(versao, trainne.getVersao());
            assigncellValue(dtInicio, trainne.getDtInicio());
            assigncellValue(dtTermino, trainne.getDtTermino());

            rowTrainne++;
        }
        autoSizeColum(sheet, columnsTrainne);
    }

}
