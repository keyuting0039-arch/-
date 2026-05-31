import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;

public class ThreeKingdom {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        
        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                
                String query = exchange.getRequestURI().getQuery();
                String inputName = ""; 
                String skillIntroduction = "请在上方输入武将技能内的特殊名词。"; 

                if (query != null && query.contains("name=")) {
                    try {
                        String rawName = query.split("name=")[1].split("&")[0];
                        inputName = URLDecoder.decode(rawName, "UTF-8").trim();
                    } catch (Exception e) {
                        skillIntroduction = "读取输入错误。";
                    }
                }

                if (!inputName.isEmpty()) {
                    switch (inputName) {
                        case "无双":
                            skillIntroduction = "<strong>【无双】</strong>：锁定技，①当你使用【杀】指定一名角色为目标后，其需使用两张【闪】才能抵消；②当你使用【决斗】指定其他角色为目标后，或成为其他角色使用【决斗】的目标后，其每次响应需打出两张【杀】。";
                            break; 
                            
                        case "极略":
                            skillIntroduction = "<strong>【极略】</strong>：你可以分别从【鬼才】、【放逐】、【倾国】、【伤逝】、【制衡】中选择一个发动。<br>"
                                    + "• <strong>【鬼才】</strong>：在任意角色判定牌生效前，打出一张手牌替换判定牌。<br>"
                                    + "• <strong>【放逐】</strong>：受到伤害后，令一名角色摸等同于你已损失体力值的牌，然后该角色翻面。<br>"
                                    + "• <strong>【倾国】</strong>：可以将任意一张黑色手牌当做【闪】使用或打出。<br>"
                                    + "• <strong>【伤逝】</strong>：锁定技，当手牌数小于已损失的体力值时，立刻将手牌补至等同于已损失体力值的数量。<br>"
                                    + "• <strong>【制衡】</strong>：出牌阶段限一次，弃置任意数量的牌，然后摸等同数量的牌。";
                            break; 
                            
                        case "奇才":
                            skillIntroduction = "<strong>【奇才】</strong>：锁定技，你使用锦囊牌无距离限制；当你使用的锦囊牌进入弃牌堆时，若此牌是本回合你使用的第一张锦囊牌，你获得之。（线上版的效果）";
                            break; 
                            
                        case "观星":
                            skillIntroduction = "<strong>【观星】</strong>：准备阶段开始时，你可以观看牌堆顶的 X 张牌（X 为存活角色数且最多为 5），然后将其中任意数量的牌以任意顺序置于牌堆顶，将其余的牌以任意顺序置于牌堆底。(注：在界限突破版本中，若存活人数小于或等于 2，观看牌堆顶的数量会固定升级为 3 张。)";
                            break;
                            
                        case "八卦阵":
                            skillIntroduction = "<strong>【八卦阵】</strong>：锁定技，每当你需要使用或打出一张【闪】时，你可以进行一次判定：若判定结果为红色（红桃或方块），则视为你使用或打出了一张【闪】。";
                            break;
                            
                        case "枭姬":
                            skillIntroduction = "<strong>【枭姬】</strong>：锁定技，每当你失去装备区里的一张装备牌时，你摸两张牌。";
                            break; 
                            
                        case "乐不思蜀":
                            skillIntroduction = "<strong>【乐不思蜀】</strong>：将【乐不思蜀】放置于该角色的判定区里。该角色的判定阶段开始时，进行一次判定：若判定结果不为红桃，则跳过该角色的出牌阶段。";
                            break; 
                            
                        case "挑衅":
                            skillIntroduction = "<strong>【挑衅】</strong>：出牌阶段限一次，你可以选择一名攻击范围能包含你的其他角色，该角色须对你使用一张【杀】，否则你弃置其一张牌。";
                            break; 
                            
                        case "激将":
                            skillIntroduction = "<strong>【激将】</strong>：当你需要使用或打出一张【杀】时，你可以令其他蜀势力角色选择是否帮你打出一张【杀】（若有角色以此法打出【杀】，则视为你使用或打出了此【杀】）。";
                            break; 
                            
                        case "兵粮寸断":
                            skillIntroduction = "<strong>【兵粮寸断】</strong>：将【兵粮寸断】放置于该角色的判定区里。该角色的判定阶段开始时，进行一次判定：若判定结果不为梅花，则跳过该角色的摸牌阶段。";
                            break; 
                            
                        case "攻心":
                            skillIntroduction = "<strong>【攻心】</strong>：出牌阶段限一次，你可以观看一名其他角色的手牌，若其中有红桃牌，你可以选择其中一张：展示并弃置之，或展示并置于牌堆顶。";
                            break; 
                            
                        case "马术":
                            skillIntroduction = "<strong>【马术】</strong>：锁定技，你计算与其他角色的距离时，始终 -1。";
                            break; 
                            
                        case "英姿":
                            skillIntroduction = "<strong>【英姿】</strong>：锁定技，摸牌阶段，你多摸一张牌；你的手牌上限始终等于你的体力上限，而非当前体力值。";
                            break; 
                            
                        case "魂姿":
                            skillIntroduction = "<strong>【魂姿】</strong>：指定一名玩家（自己除外）抽取X后丢1张牌或抽取1张牌后丢X （X为武将目前血量）";
                            break; 
                        
                        default: 
                            skillIntroduction = "暂未收录名词 \"<span style='color:red;'>" + inputName + "</span>\" 的释义。请尝试输入：无双、极略、观星、八卦阵 等。";
                            break;
                    }
                }

                String htmlResponse = "<html>"
                        + "<head><meta charset='UTF-8'><title>三国杀特殊名词查询系统</title></head>"
                        + "<body style='font-family: Arial, sans-serif; margin: 40px; text-align: center; background-color: #f7f9fa;'>"
                        + "  <div style='max-width: 600px; margin: 0 auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0,0,0,0.1);'>"
                        + "    <h2>⚔️ 三国杀特殊名词查询 ⚔️</h2><br>"
                        + "    <form action='/' method='get'>"
                        + "      <input type='text' name='name' placeholder='输入技能/装备（如：无双）' value='" + inputName + "' style='font-size: 16px; padding: 8px; width: 250px; border: 1px solid #ccc; border-radius: 4px;'>"
                        + "      <input type='submit' value='查询释义' style='font-size: 16px; padding: 8px 20px; margin-left: 10px; background-color: #5cb85c; color: white; border: none; border-radius: 4px; cursor: pointer;'>"
                        + "    </form><br><hr style='border: 0; border-top: 1px solid #eee;'><br>"
                        + "    <div style='padding: 20px; background-color: #f0f4f7; border-radius: 5px; text-align: left; font-size: 16px; line-height: 1.6; color: #333;'>"
                        + "      " + skillIntroduction
                        + "    </div>"
                        + "  </div>"
                        + "</body>"
                        + "</html>";

                exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                exchange.sendResponseHeaders(200, htmlResponse.getBytes("UTF-8").length);
                
                OutputStream os = exchange.getResponseBody();
                os.write(htmlResponse.getBytes("UTF-8"));
                os.close();
            }
        });

        server.start();
        System.out.println("系统已在 8080 端口跑起来了！");
    }
}
