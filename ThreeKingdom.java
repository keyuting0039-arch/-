import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;

public class ThreeKingdom {

    public static void main(String[] args) throws IOException {
        // 在 8080 端口启动你专属的网页服务器
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        
        // 绑定核心读取与技能查找逻辑
        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                
                // ------------------ 【核心：读取你输入的武将】 ------------------
                String query = exchange.getRequestURI().getQuery();
                String inputName = ""; 
                String skillIntroduction = "请在上方输入武将名字查询技能介绍。"; // 默认提示信息

                // 检测你在网页输入框里是否提交了内容
                if (query != null && query.contains("name=")) {
                    try {
                        String rawName = query.split("name=")[1].split("&")[0];
                        // 读取你在输入框里打的字，还原成干净的中文
                        inputName = URLDecoder.decode(rawName, "UTF-8").trim();
                    } catch (Exception e) {
                        skillIntroduction = "读取输入错误。";
                    }
                }
                // -----------------------------------------------------------

                // ------------------ 【核心：根据输入读取技能（匹配逻辑）】 ------------------
                if (!inputName.isEmpty()) {
                    // Java 在内存中匹配你刚才输入的那个东西
                    if (inputName.equals("曹操")) {
                        skillIntroduction = "<strong>【奸雄】</strong>：锁定技，每当你受到伤害后，你可以获得造成此伤害的牌。";
                    } else if (inputName.equals("刘备")) {
                        skillIntroduction = "<strong>【仁德】</strong>：出牌阶段限一次，你可以将任意张手牌交给其他角色。当你以此法交出的牌达到两张或更多时，你回复1点体力。";
                    } else if (inputName.equals("孙权")) {
                        skillIntroduction = "<strong>【制衡】</strong>：出牌阶段限一次，你可以弃置任意张牌，然后摸等量的牌。";
                    } else {
                        // 如果输入的不是这三个人
                        skillIntroduction = "暂未收录武将 \"<span style='color:red;'>" + inputName + "</span>\" 的技能。请尝试输入：曹操、刘备 或 孙权。";
                    }
                }
                // -----------------------------------------------------------

                // ------------------ 【核心：在网页上画出界面】 ------------------
                String htmlResponse = "<html>"
                        + "<head>"
                        + "  <meta charset='UTF-8'>"
                        + "  <title>武将技能查询系统</title>"
                        + "</head>"
                        + "<body style='font-family: Arial, sans-serif; margin: 40px; text-align: center; background-color: #f7f9fa;'>"
                        + "  <div style='max-width: 600px; margin: 0 auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0,0,0,0.1);'>"
                        + "    <h2>⚔️ 三国杀武将技能查询 ⚔️</h2>"
                        + "    <br>"
                        + "    "
                        + "    <form action='/' method='get'>"
                        + "      <input type='text' name='name' placeholder='输入武将名字（如：曹操）' value='" + inputName + "' style='font-size: 16px; padding: 8px; width: 250px; border: 1px solid #ccc; border-radius: 4px;'>"
                        + "      <input type='submit' value='查询技能' style='font-size: 16px; padding: 8px 20px; margin-left: 10px; background-color: #5cb85c; color: white; border: none; border-radius: 4px; cursor: pointer;'>"
                        + "    </form>"
                        + "    <br><hr style='border: 0; border-top: 1px solid #eee;'>"
                        + "    <br>"
                        + "    "
                        + "    <div style='padding: 20px; background-color: #f0f4f7; border-radius: 5px; text-align: left; font-size: 16px; line-height: 1.6; color: #333;'>"
                        + "      " + skillIntroduction
                        + "    </div>"
                        + "  </div>"
                        + "</body>"
                        + "</html>";
                // -----------------------------------------------------------

                // 把带有技能介绍的网页传回浏览器
                exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                exchange.sendResponseHeaders(200, htmlResponse.getBytes("UTF-8").length);
                
                OutputStream os = exchange.getResponseBody();
                os.write(htmlResponse.getBytes("UTF-8"));
                os.close();
            }
        });

        // 启动常驻服务
        server.start();
