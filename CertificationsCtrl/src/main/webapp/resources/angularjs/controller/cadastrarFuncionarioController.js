/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var app = angular.module("rhApp");
app.controller("cadastrarFuncionario", function ($scope, crudService, $uibModal, broadCastService, exportService) {
    $scope.funcionario = {};
    $scope.funcionario.formacoes = [];
    $scope.funcionario.idiomas = [];
    $scope.funcionario.certificacoes = [];
    $scope.funcionario.treinamentos = [];
    $scope.funcionario.telefone = "(9608)";
    $scope.newFormacoes;
    $scope.newCertificacoes;
    $scope.novoIdioma;
    $scope.newTreinamentos;
    $scope.indexColor;
    $scope.applyClass = function (option, index) {
        $scope.indexColor = index;
        $scope.funcionario.checked = false;
    };

    $scope.languages = ["Português", "Inglês", "Espanhol", "Frânces", "Alemão", "Italiano", "Grego", "Russo", "Indi", "Japônes", "Chinês", "Mandarim", "Hebraíco"];
    $scope.options = ["Dados Cadastrais", "Formação Acadêmica", "Idioma", "Certificação", "Treinamentos"];
    $scope.optionsBoolean = [false, false, false, false];
    $scope.booleanForm = false;
    $scope.ativarForm = function (option) {
        for (var i = 0; i < $scope.optionsBoolean.length; i++) {
            $scope.optionsBoolean[i] = false;
        }
        $scope.booleanForm = true;
        $scope.optionsBoolean[option] = true;

    };
    $scope.niveis = ["Nativo", "Básico", "Intermediário", "Avançado", "Fluente"];
    $scope.pageSize = 4;
//    $scope.certificacoes = searchCertifications();

    $scope.animationsEnabled = true;
    $scope.certifications;

    $scope.changeEnable = function (value) {
        $scope.funcionario.habilitado = value;
    };

    function validation(employee) {
        console.log(employee);
        var boolean = true;

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
    $scope.UpperCase = function (txt, scope) {
        txt.toLocaleUpperCase();
        console.log(scope);
    };
    $scope.save = function () {
        console.log($scope.funcionario);
        if (validation($scope.funcionario)) {
            var ajax = crudService.save($scope.funcionario);
            ajax.success(function (data) {
                broadCastService.broadCastAlertSuccess("Salvo com Sucesso " + " " + data.nome);
            }).error(function (error) {
                broadCastService.broadCastAlertDanger("Error " + error);
            });

        } else {
            broadCastService.broadCastAlertDanger("Informação inválida");
        }

        $scope.funcionario = {};
        $scope.funcionario.formacoes = [];
        $scope.funcionario.idiomas = [];
        $scope.funcionario.certificacoes = [];
    };
    $scope.reset = function () {
        $scope.funcionario = {};
    };

    $scope.saveRow = function () {
        $scope.funcionario.checked = !$scope.funcionario.checked;
        console.log($scope.funcionario);
    };
    $scope.addRow = function (novaInformacao, template) {
        if (template === 'formacao') {
            if (angular.isUndefined(novaInformacao) || angular.isUndefined(novaInformacao)) {
                broadCastService.broadCastAlertDanger("Erro " + "é necessário preencher o nome da " + template + " para adicioná-la.");
            } else {
                if (angular.isUndefined(novaInformacao.curso) || novaInformacao.curso === "" || angular.isUndefined(novaInformacao.instituicao) || novaInformacao.instituicao === "" || angular.isUndefined(novaInformacao.nivel) || novaInformacao.nivel==="") {
                    broadCastService.broadCastAlertDanger("Erro " + "é necessário preencher o nível, curso e a instituição da " + template + " para adicioná-la.");
                } else {
                    $scope.funcionario.formacoes.push(novaInformacao);
                    $scope.newFormacoes = null;
                }
            }
            console.log($scope.funcionario);
        } else if (template === 'certificacao') {
            if (angular.isUndefined(novaInformacao) || angular.isUndefined(novaInformacao)) {
                broadCastService.broadCastAlertDanger("Erro " + "é necessário preencher o nome da " + template + " para adicioná-la.");
            } else {
                if (angular.isUndefined(novaInformacao.nome) || novaInformacao.nome === "" || angular.isUndefined(novaInformacao.empresa) || novaInformacao.empresa === "") {
                    broadCastService.broadCastAlertDanger("Erro " + "é necessário preencher a certificadora e o exame da " + template + " para adicioná-la.");
                } else {
                    $scope.funcionario.certificacoes.push(novaInformacao);
                    $scope.newCertificacoes = null;
                }
            }
            console.log($scope.funcionario);
        } else if (template === 'idioma') {
            if (angular.isUndefined(novaInformacao) || angular.isUndefined(novaInformacao)) {
                broadCastService.broadCastAlertDanger("Erro " + "é necessário preencher o nome do " + template + " para adicioná-lo.");
            } else {
                if (angular.isUndefined(novaInformacao.nome) || novaInformacao.nome === "" || angular.isUndefined(novaInformacao.nivel) || novaInformacao.nivel === "") {
                    broadCastService.broadCastAlertDanger("Erro " + "é necessário preencher o idioma e nível do " + template + " para adicioná-la.");
                } else {
                    $scope.funcionario.idiomas.push(novaInformacao);
                    $scope.novoIdioma = null;
                }
            }
            console.log($scope.funcionario);
        } else if (template === "treinamento") {
            if (angular.isUndefined(novaInformacao) || angular.isUndefined(novaInformacao)) {
                broadCastService.broadCastAlertDanger("Erro " + "é necessário preencher o nome do " + template + " para adicioná-lo.");
            } else {
                if (angular.isUndefined(novaInformacao.treinamento) || novaInformacao.treinamento === "" || angular.isUndefined(novaInformacao.certificadora) || novaInformacao.certificadora === "") {
                    broadCastService.broadCastAlertDanger("Erro " + "é necessário preencher o campo Treinamento e Certificadora " + template + " para adicioná-lo.");
                } else {
                    $scope.funcionario.treinamentos.push(novaInformacao);
                    $scope.newTreinamentos = null;
                }
            }
        }

    };
    $scope.editRow = function (informacao) {
        $scope.funcionario.checked = !$scope.funcionario.checked;
    };
    $scope.removeRow = function (index, template) {
        if (template === "formacao") {
            $scope.funcionario.formacoes.splice(index, 1);
        } else if (template === "certificacao") {
            $scope.funcionario.certificacoes.splice(index, 1);
        } else if (template === "idioma") {
            $scope.funcionario.idiomas.splice(index, 1);
        } else if (template === "treinamento") {
            $scope.funcionario.treinamentos.splice(index, 1);
        }

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

    $scope.readExcel = function () {
        exportService.readExcel();
    };
});
