/*global cordova, module*/

module.exports = {
    htjcFaceDetectAction: function (name, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "HTJC", "htjcFaceDetectAction", [name]);
    }
};
