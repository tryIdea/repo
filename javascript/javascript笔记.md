 /**
 * @Description: Description
 * @Author:      dfg
 * @DateTime:    2015-05-22 15:12:39
 */

####recursiveObject
//循环遍历一个对象及其子对象的属性和值
        function recursiveObject(obj) {            

            for(var o in obj) {
              if(obj.hasOwnProperty(o)) {
                  if(typeof obj[o] == "object") {                  
                    recursiveObject(obj[o]);
                  } else {                 
                    console.log(o+"="+obj[o]);
                  }
              }                                
                
            }
        }

####javascript加断点调试
debugger;

####将json对象转换成字符串
JSON.stringify(params);

####将字符串转换成json对象
$.parseJSON();

####console日志输出格式
[LOG][Flowjs][2016-03-06 22:30:00] >>> 步骤结束：绑定传入参数