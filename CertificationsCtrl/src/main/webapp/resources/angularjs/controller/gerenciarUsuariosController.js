/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var app = angular.module("rhApp");
app.controller("gerenciarUsuariosController", function ($scope, crudService, broadCastService) {

    $scope.newRoles = [];

    $scope.isEmployeeSelected = function (employee, index) {
        console.log("employee");
        console.log(employee);
        $scope.checkRole = 0;
        $scope.selecionado = employee;
        console.log($scope.selecionado.userandrole);
        if ($scope.selecionado.userandrole !== null) {
            angular.forEach($scope.selecionado.userandrole.authorities, function (value, index) {
                console.log("ok");
                switch (value) {
                    case "ROLE_EMPLOYEE":
                        $scope.checkRole = 1;
                        break;
                    case "ROLE_ADMIN":
                        $scope.checkRole = 2;
                        break;
                    case "ROLE_MANAGER":
                        $scope.checkRole = 3;
                        break;
                }
                ;
            });

            console.log("ok");
            switch ($scope.selecionado.userandrole.enabled) {
                case true:
                    $scope.checkEnable = 1;
                    break;
                case false:
                    $scope.checkEnable = 2;
                    break;
            }
            ;
        }
        console.log($scope.checkRole);

    };
    $scope.selectEnables = function (boolean) {
        switch (boolean) {
            case true:
                $scope.checkEnable = 1;
                break;
            case false:
                $scope.checkEnable = 2;
                break;
        }
        ;
        if($scope.selecionado.userandrole.enabled === boolean){
            $scope.selecionado.userandrole.enabled = "";
        }else{
            $scope.selecionado.userandrole.enabled = boolean;
        }          
        console.log($scope.selecionado.userandrole.enabled);
        
    };
    $scope.save = function (employee) {
        if (validation(employee)) {
            var ajax = crudService.save(employee);
            ajax.success(function (data) {
                broadCastService.broadCastAlertSuccess("Alterado com Sucesso " + " " + data.nome);
                $scope.funcionario = {};
            }).error(function (error) {
                broadCastService.broadCastAlertDanger("Error " + error);
            });
        } else {
            broadCastService.broadCastAlertDanger("Informação inválida");
        }
        console.log(employee);
    };
    function validation(employee) {
        console.log(employee);
        var boolean = true;
        if (employee.userandrole.userName === null || employee.userandrole.userName === undefined || employee.userandrole.userName === "") {
            boolean = false;
        }
        if (employee.userandrole.password === null || employee.userandrole.password === undefined || employee.userandrole.password === "") {
            boolean = false;
        }
        if (employee.userandrole.authorities[0] === null || $scope.selecionado.userandrole.authorities[0] === undefined || $scope.selecionado.userandrole.authorities[0] === "") {
            boolean = false;
        }
        if (employee.userandrole.enabled === null || employee.userandrole.enabled === undefined || employee.userandrole.enabled === "") {
            boolean = false;
        }

        return boolean;
    }

    var Roles = [];
    $scope.selectRole = function (role, index) {
        if (angular.isDefined($scope.selecionado.userandrole)) {
            //Roles[0]=role;
            $scope.selecionado.userandrole.authorities = checkRoleRepeated(role, $scope.selecionado.userandrole.authorities);
            $scope.checkRole = index;

        } else {
            Roles[0] = role;
            $scope.selecionado.userandrole.authorities = Roles;
            $scope.checkRole = index;
        }
        console.log($scope.selecionado.userandrole.authorities);


    };
    function checkRoleRepeated(role, roles) {
        var cont = 0;
        console.log(angular.isUndefined(roles));
        if (!angular.isUndefined(roles)) {
            for (var i = 0; i < roles.length; i++) {
                if (roles[i] === role) {
                    roles.splice(i, 1);
                    cont = -4;
                }
                cont++;
            }
            if (cont === roles.length) {
                roles[0] = role;
            }
        } else {
            var array=[];
            array.push(role);
            roles = array;
        }
        return roles;
    }
});
