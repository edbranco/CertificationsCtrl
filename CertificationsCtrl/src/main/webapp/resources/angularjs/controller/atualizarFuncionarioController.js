/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var app = angular.module("rhApp");
app.controller("atualizarFuncionario", function ($scope, crudService, $uibModal, broadCastService, exportService, $location, $anchorScroll) {

    console.log($scope.userLogged);
    $scope.funcionarios;
    $scope.selecionado;
    $scope.languages = ["Português", "Inglês", "Espanhol", "Frânces", "Alemão", "Italiano", "Grego", "Russo", "Indi", "Japônes", "Chinês", "Mandarim", "Hebraíco"];
    $scope.niveis = ["Avançado", "Básico", "Fluente", "Intermediário", "Nativo"];
    $scope.options = ["Dados Cadastrais", "Formação Acadêmica", "Idioma", "Certificação", "Treinamentos", "Usuário"];
    $scope.optionsBoolean = [false, false, false, false];
    $scope.newFormacoes;

    $scope.newTreinamentos;
    $scope.novoIdioma;
    $scope.indexColor;
    $scope.applyClass = function (option, index) {
        $scope.indexColor = index;
        $scope.selecionado.checked = false;
    };
    function applyClass(index) {
        $scope.indexColor = index;
    }
    ;
    $scope.buscar = function (nome) {
        console.log(nome);
        searchByName(nome);
    };
    $scope.ativarForm = function (option) {
        for (var i = 0; i < $scope.optionsBoolean.length; i++) {
            $scope.optionsBoolean[i] = false;
        }
        $scope.booleanForm = true;
        $scope.optionsBoolean[option] = true;
    };
    $scope.selectCopiaFormacao = function (copia) {
        $scope.newFormacoes.copiaCertificado = copia;
        console.log($scope.newFormacoes.copiaCertificado);
        console.log($scope.newFormacoes);

    };
    $scope.isEmployeeSelected = function (employee, index) {
        $("#loader").show();
        console.log("employee");
        console.log(employee);
        $scope.ativarForm(0);
        applyClass(0);
        $scope.selecionado = employee;
        $("#loader").hide();
    };
    $scope.changeEnable = function (value) {
        console.log(value);
        $scope.selecionado.habilitado = value;
        console.log($scope.selecionado.habilitado);
    };
    $scope.save = function (select) {
        console.log(select);
        if (angular.isUndefined(select.habilitado) || select.habilitado === "" || select.habilitado === null) {
            select.habilitado = true;
        }
        if (validation(select)) {
            var ajax = crudService.save(select);
            ajax.success(function (data) {
                broadCastService.broadCastAlertSuccess("Alterado com Sucesso " + " " + data.nome);
                $scope.funcionario = {};
            }).error(function (error) {
                broadCastService.broadCastAlertDanger("Error " + error);
            });

        } else {
            broadCastService.broadCastAlertDanger("Informação inválida");
        }


    };
    $scope.remove = function (employee) {
        var boolean = confirm("Deseja excluir?");
        if (boolean) {
            angular.forEach($scope.employees, function (value, index) {
                if (employee.nome === value.nome) {
                    $scope.employees.splice(index, 1);
                    console.log("ok");
                }
            });
            var ajax = crudService.remove(employee);
            ajax.success(function (data) {
                broadCastService.broadCastAlertSuccess("Funcionário removido com sucesso " + " " + data.nome);
                $scope.funcionario = {};
            }).error(function (error) {
                broadCastService.broadCastAlertDanger("Error " + error);
            });
            $scope.selecionado = false;

        }


    };
    $scope.selecionar = function (funcionario) {
        $scope.selecionado = funcionario;
        searchCertifications();
    };
//
    function validation(employee) {
        console.log(employee);
        var boolean = true;
        if (employee.idFuncionario === null || employee.idFuncionario === undefined || employee.idFuncionario === "") {
            boolean = false;
        }
        if (employee.nome === null || employee.nome === undefined || employee.nome === "") {
            boolean = false;
        }
        if (employee.dtAdmissao === null || employee.dtAdmissao === undefined || employee.dtAdmissao === "") {
            boolean = false;
        }
        if (employee.gestor === null || employee.gestor === undefined || employee.gestor === "") {
            boolean = false;
        }
        if (employee.cargo === null || employee.cargo === undefined || employee.cargo === "") {
            boolean = false;
        }
        if (employee.area === null || employee.area === undefined || employee.area === "") {
            boolean = false;
        }
        if (employee.habilitado === null || employee.habilitado === undefined || employee.habilitado === "") {
            boolean = false;
        }

        return boolean;
    }
    function successOnSearch(data) {
        console.log(data);
        console.log(data.employees);
        $scope.employees = data.employees;
        $scope.total = data.totalEntities;
    }
    function errorOnSearch(error, status) {
        console.log("ajax error:" + error);
        console.log("ajax status:" + status);
    }
    function searchForPagination(newPage, pageSize, searchFilter) {
        if (pageSize === null) {
            pageSize = 1;
        }
        if (searchFilter === undefined) {
            searchFilter = "";
        }
        crudService.searchFast(newPage, pageSize, searchFilter, successOnSearch, errorOnSearch);
    }
    $scope.saveRow = function () {
        $scope.selecionado.checked = !$scope.selecionado.checked;

        console.log($scope.selecionado);
    };
    $scope.addRow = function (novaInformacao, template) {
        if (template === 'formacao') {
            if (angular.isUndefined(novaInformacao) || angular.isUndefined(novaInformacao)) {
                broadCastService.broadCastAlertDanger("Erro " + "é necessário preencher o nome da " + template + " para adicioná-la.");
            } else {
                if (angular.isUndefined(novaInformacao.curso) || novaInformacao.curso==="" || angular.isUndefined(novaInformacao.instituicao) || novaInformacao.instituicao==="" || angular.isUndefined(novaInformacao.nivel) || novaInformacao.nivel==="") {
                    broadCastService.broadCastAlertDanger("Erro " + "é necessário preencher o nível, curso e a instituição da " + template + " para adicioná-la.");
                } else {
                    $scope.selecionado.formacoes.push(novaInformacao);
                    $scope.newFormacoes = null;
                }
            }
            console.log($scope.selecionado);
        } else if (template === 'certificacao') {
            if (angular.isUndefined(novaInformacao) || angular.isUndefined(novaInformacao)) {
                broadCastService.broadCastAlertDanger("Erro " + "é necessário preencher o nome da " + template + " para adicioná-la.");
            } else {
                if (angular.isUndefined(novaInformacao.nome)|| novaInformacao.nome==="" || angular.isUndefined(novaInformacao.empresa) || novaInformacao.empresa==="") {
                    broadCastService.broadCastAlertDanger("Erro " + "é necessário preencher a certificadora e o exame da " + template + " para adicioná-la.");
                } else {
                    $scope.selecionado.certificacoes.push(novaInformacao);
                    $scope.newCertificacoes = null;
                }
            }
            console.log($scope.selecionado);
        } else if (template === 'idioma') {
            if (angular.isUndefined(novaInformacao) || angular.isUndefined(novaInformacao)) {
                broadCastService.broadCastAlertDanger("Erro " + "é necessário preencher o nome do " + template + " para adicioná-lo.");
            } else {
                if (angular.isUndefined(novaInformacao.nome) || novaInformacao.nome==="" || angular.isUndefined(novaInformacao.nivel) || novaInformacao.nivel==="") {
                    broadCastService.broadCastAlertDanger("Erro " + "é necessário preencher o idioma e nível do " + template + " para adicioná-la.");
                } else {
                    $scope.selecionado.idiomas.push(novaInformacao);
                    $scope.novoIdioma = null;
                }
            }
            console.log($scope.selecionado);
        } else if (template === "treinamento") {
            if (angular.isUndefined(novaInformacao) || angular.isUndefined(novaInformacao)) {
                broadCastService.broadCastAlertDanger("Erro " + "é necessário preencher o nome do " + template + " para adicioná-lo.");
            } else {
                if (angular.isUndefined(novaInformacao.treinamento) || novaInformacao.treinamento === "" || angular.isUndefined(novaInformacao.certificadora) || novaInformacao.certificadora==="") {
                    broadCastService.broadCastAlertDanger("Erro " + "é necessário preencher o campo Treinamento e Certificadora " + template + " para adicioná-lo.");
                } else {
                    $scope.selecionado.treinamentos.push(novaInformacao);
                    $scope.newTreinamentos = null;
                }
            }
        }

    };
    $scope.editRow = function (formacao) {
        $scope.selecionado.checked = !$scope.selecionado.checked;
    };
    $scope.removeRow = function (index, template) {
        if (template === "formacao") {
            $scope.selecionado.formacoes.splice(index, 1);
        } else if (template === "certificacao") {
            $scope.selecionado.certificacoes.splice(index, 1);
        } else if (template === "idioma") {
            $scope.selecionado.idiomas.splice(index, 1);
        } else if (template === "treinamento") {
            $scope.selecionado.treinamentos.splice(index, 1);
        }

    };

    var columns = ["Nome", "Cargo", "Data de Admissao", "Área", "Gestor", "Email", "Telefone", "Celular"];

    $scope.exportType = function (type) {
        console.log($scope.habilitado);
        exportService.exportType(type, $scope.habilitado);
        window.open("export/exportFile" + "/" + type + "/" + $scope.habilitado);


    };
    $scope.singleReport = function (idEmployee) {
        console.log(idEmployee);
        exportService.exportSingleReport(idEmployee);
        window.open("export/exportSingleReport" + "/" + idEmployee);
    };
    $scope.cancelRow = function (information) {
        if (information === "formacao") {
            $scope.newFormacoes = [];
        } else if (information === "certificacao") {
            $scope.newCertificacoes = [];
        } else if (information === "idiomas") {
            $scope.novoIdioma = [];
        } else if (information === "treinamento") {
            $scope.newTreinamentos = [];
        }
    };

    $scope.backToTop = function (id) {
        console.log(id);
        $location.hash(id);
        $anchorScroll();
    };

    $scope.initializeDatepicker = function () {
        $('.dtpickerMonth').datepicker({
            startView: 2,
            minViewMode: 1,
            format: 'mm-yyyy'
        });
    };
});