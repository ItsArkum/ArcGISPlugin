var exec = require('cordova/exec');

exports.showMap = function(success, error) {
  exec(success, error, 'ArcGISPlugin', 'showMap', []);
};

exports.showOfflineMap = function(success, error) {
  exec(success, error, 'ArcGISPlugin', 'showOfflineMap', []);
}