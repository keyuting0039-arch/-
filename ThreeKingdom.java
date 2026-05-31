import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;

public class ThreeKingdom {

    public static void main(String[] args) throws IOException {
        // 在 8080 端口启动你专属的网页服务器（由你电脑或云端独自运行，不连外网）
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        
        // 绑定核心读取与显示逻辑
        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                
                // ------------------ 【核心：读取你输入的东西】 ------------------
                String query = exchange.getRequestURI().getQuery();
                String name = "等待输入..."; // 初始默认状态

                // 如果检测到你在网页输入框里提交了内容
                if (query != null && query.contains("name=")) {
                    try {
                        // 1. 从网络数据包中切出你打的字
                        String rawName = query.split("name=")[1].split("&")[0];
                        // 2. 将传输过程中的中文编码解码，还原成你输入的“干净中文”
                        name = URLDecoder.decode(rawName, "UTF-8");
                    } catch (Exception e) {
                        name = "读取错误: " + e.getMessage();
                    }
                }
                // -----------------------------------------------------------

                // ------------------ 【核心：在网页上画出界面】 ------------------
                // 这部分代码负责在网页上画出提示字、输入框和提交按钮
                String htmlResponse = "<html>"
                        + "<head>"
                        + "  <meta charset='UTF-8'>"
                        + "  <title>三国杀系统</title>"
                        + "</head>"
                        + "<body style='font-family: Arial, sans-serif; margin: 40px; text-align: center;'>"
                        + "  <h2>⚔️ 三国杀武将录入系统 ⚔️</h2>"
                        + "  <br>"
                        + "  "
                        + "  <form action='/' method='get'>"
                        + "    <label style='font-size: 18px;'>输入武将名字: </label>"
                        + "    <input type='text' name='name' style='font-size: 16px; padding: 5px; width: 200px;'>"
                        + "    <input type='submit' value='提交' style='font-size: 16px; padding: 5px 15px; margin-left: 10px;'>"
                        + "  </form>"
                        + "  <br><br><hr>"
                        + "  "
                        + "  <h3>系统刚才读取到的武将是: </h3>"
                        + "  <h1 style='color: #d9534f;'>" + name + "</h1>"
                        + "</body>"
                        + "</html>";
                // -----------------------------------------------------------

                // 将做好的网页和读取到的中文结果，一齐返回给你的浏览器
                exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                exchange.sendResponseHeaders(200, htmlResponse.getBytes("UTF-8").length);
                
                OutputStream os = exchange.getResponseBody();
                os.write(htmlResponse.getBytes("UTF-8"));
                os.close();
            }
        });

        // 开启常驻服务
        server.start();
        System.out.println("服务已成功启动！");
    }
}
