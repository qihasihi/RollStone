上传机制：系统把选择的文件分割上传，每次传输指定大小的数据块，默认的数据块大小为 2M

1、运行 reg.bat 初始化客户端运行环境

2、创建测试web服务器， 把下列文件放到根目录下 
     chrome.html(谷歌浏览器的测试页面)
     demo.html(IE的测试页面)
     upload_file.php
     css (目录)
     js (目录)
		
3、修改 chrome.html
     240 行 init.url = 'http://202.99.47.94:8080/ffmpeg/upload1.jsp?jsessionid=aaatCu3yQxMmN-Rru135t&res_id=10000';
     转换 jsp 是需要修改这个的 jsessionid 和 res_id
     
     241 行 init.chunksize = 1024 * 256; 	     
     这里可以修改文件分割的大小

     修改 demo.html 240 行
     240 行 init.url = 'http://202.99.47.94:8080/ffmpeg/upload1.jsp?jsessionid=aaatCu3yQxMmN-Rru135t&res_id=10000';
     转换 jsp 是需要修改这个的 jsessionid 和 res_id
     
     241 行 init.chunksize = 1024 * 256; 	     
     这里可以修改文件分割的大小

 

4、在客户端安装 logtools\CS4.exe , 这个工具可以方便的查看系统输出的日志