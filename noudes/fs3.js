var http = require('http');
var fs = require('fs');

const hostname = '127.0.0.1';
const port = 3000;

const server = http.createServer(function (req, res) {
	console.log('url : ' + req.url);
	if(req.url === '/ajax'){
		fs.readFile('ajax1.html', function(err, data) {
			res.writeHead(200, {'Content-Type': 'text/html'});
			res.write(data);
			return res.end();
		});
	} else if(req.url === '/ajax_resp'){
		fs.readFile('ajax_resp.html', function(err, data) {
			res.writeHead(200, {'Content-Type': 'text/html'});
			res.write(data,reloj);
			return res.end();
		});
	} else if(req.url === '/page1'){
		fs.readFile('page1.html', function(err, data) {
			res.writeHead(200, {'Content-Type': 'text/html'});
			res.write(data);
			return res.end();
		});
	} else if(req.url === '/page2'){
		fs.readFile('page2.html', function(err, data) {
			res.writeHead(200, {'Content-Type': 'text/html'});
			res.write(data);
			return res.end();
		});
	} else {
		res.statusCode = 404;
		res.end();
	}
});

server.listen(port, hostname, () => {
  console.log('Server running at http://${hostname}:${port}/');
});
// separador
function timer() {
	const x = new Date;
	var hora = x.getHours();
	var min = x.getMinutes();
	var sec = x.getSeconds();
	var dia = x.getDate();
	var mes = x.getMonth();
	var año = x.getFullYear();
	var reloj =mes+"/" + dia +"/" + año +"   " + hora + ":" + min + ":" + sec;
	 
	  setTimeout(timer, 1000)
  }
const x = new Date;
var hora = x.getHours();
var min = x.getMinutes();
var sec = x.getSeconds();
var dia = x.getDate();
var mes = x.getMonth();
var año = x.getFullYear();
var reloj =mes+"/" + dia +"/" + año +"   " + hora + ":" + min + ":" + sec;
setTimeout(timer, 1000)
console.log('App started');
