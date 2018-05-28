module.exports = {

/**
* Action blueprints:
*    `/gettoken/get`
*/
get: function (req, res) {

// Send a JSON response

  var java = require('java')
   java.classpath.push("CLASS PATH DEL .JAR")
  var javaObject = java.newInstanceSync("com....")


    return res.json({
      hello: javaObject.getTokenSync()
    }); 
},


/**
* Overrides for the settings in `config/controllers.js`
* (specific to GetTokenController)
*/
_config: {}

};
