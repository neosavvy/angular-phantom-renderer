var system = require('system'),
    fs = require('fs'),
    page = new WebPage(),
    url = system.args[1],
    output = system.args[2],
    result;


page.open(url, function (status) {
    if (status !== 'success') {
      console.log('FAILED to load the url');
      phantom.exit();
    } else {
      result = page.evaluate(function(){
          var html, doc;
  
          html = document.querySelector('html');

          return html.outerHTML;
      });

      if(output){
        
        var rendered = fs.open(output,'w');
        rendered.write(result);
        rendered.flush();
        rendered.close();
        
      }else{
        
        console.log(result);
        
      }
    }
    phantom.exit();
});