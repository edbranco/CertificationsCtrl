/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var app = angular.module("rhApp");
app.controller('loginController', function ($scope, $location,broadCastService) {
    $scope.err = false;
    showMessage();
    function showMessage() {
        var url = $location.absUrl();
        if (url.includes("login?error")) {
            broadCastService.broadCastAlertDanger("Erro, " + "Usuário ou Senha Inválido");
        }
    }
});