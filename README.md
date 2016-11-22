# 需要用到Cordova框架
# Cordova HTJCFaceLiveDetectPlugin


一个简单的插件，调用活体检测SDK。。。。


## Using

Create a new Cordova Project

    $ cordova create DEMO
    
Install the plugin

    $ cd DEMO
    $ cordova plugin add https://github.com/Gengpp/HTJCFaceLiveDetectPlugin.git
    

Edit `www/js/index.js` and add the following code inside `onDeviceReady`

```js
   var success = function(message) {
        alert(message);
    }
    var failure = function(error) {
        alert(error);
    }
    htjc.htjcFaceDetectAction("",success,failure);
```

Install iOS or Android platform

    cordova platform add ios
    cordova platform add android
    
Run the code

    cordova run ios
    cordova run android

## More Info

关于cordova的知识，看[CORDOVA 官网](http://cordova.apache.org)

For more information on setting up Cordova see [the documentation](http://cordova.apache.org/docs/en/latest/guide/cli/index.html)

For more info on plugins see the [Plugin Development Guide](http://cordova.apache.org/docs/en/latest/guide/hybrid/plugins/index.html)




