var app = angular.module('CocktailApp', ['ngRoute']);

app.config(function($locationProvider) {
    $locationProvider.hashPrefix('');
});

app.config(function($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'menu.html'
        })
        .when('/cocktails', {
            templateUrl: 'cocktail-list.html',
            controller: 'CocktailListController'
        })
        .when('/cocktails/:id', {
            templateUrl: 'cocktail-detail.html',
            controller: 'CocktailDetailController'
        })
        .when('/ingredients', {
            templateUrl: 'ingredient-list.html',
            controller: 'IngredientListController'
        })
        .when('/ingredients/:id', {
            templateUrl: 'ingredient-detail.html',
            controller: 'IngredientDetailController'
        })
        .when('/shopping', {
            templateUrl: 'shopping.html',
            controller: 'ShoppingController'
        })
        .otherwise({
            redirectTo: '/'
        });
});

app.controller('CocktailListController', function($scope, $http) {
    $http.get('/api/cocktails')
        .then(function(response) {
            $scope.cocktails = response.data;
        });
});

app.controller('CocktailDetailController', function($scope, $routeParams, $http) {
    $http.get('/api/cocktails/' + $routeParams.id)
        .then(function(response) {
            $scope.cocktail = response.data;
        });
});

app.controller('IngredientListController', function($scope, $http) {
    $http.get('/api/ingredients')
        .then(function(response) {
            $scope.ingredients = response.data;
        });
});

app.controller('IngredientDetailController', function($scope, $routeParams, $http) {
    $http.get('/api/ingredients/' + $routeParams.id)
        .then(function(response) {
            $scope.ingredient = response.data;
        });
});

app.controller('ShoppingController', function($scope, $http) {
    // initialize shoppingList as empty map
    $scope.shoppingList = {}; // new Map();

    $http.get('/api/shopping')
        .then(function(response) {
            $scope.cocktailsList = response.data;
        });

    // function to add missing ingredients to shopping list
    $scope.addToShoppingList = function(index) {
        const ingredients = $scope.cocktailsList[index].missingIngredients;
        for (var i = 0; i < ingredients.length; i++) {
            var ingredient = ingredients[i];
            $scope.shoppingList[ingredient] = true;
            // $scope.shoppingList.set(ingredient, true);
        }
    };

    $scope.shoppingListNotEmpty = function() {
        return Object.keys($scope.shoppingList).length > 0;
    };
});
