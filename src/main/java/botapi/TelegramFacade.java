package botapi;

import cache.UserAdCache;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramFacade {
    private BotStateContext botStateContext;
    private UserAdCache userAdCache;

    public TelegramFacade(BotStateContext botStateContext, UserAdCache userAdCache) {
        this.botStateContext = botStateContext;
        this.userAdCache = userAdCache;
    }

    public SendMessage handleUpdate(Update update){
        SendMessage replyMessage = null;

        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            replyMessage = handleInputMessage(message);
        }
        System.out.println("message HandleUpdate");
        return replyMessage;
    }

    private SendMessage handleInputMessage(Message message) {
        System.out.println("message HandleInput");
        String inputMsg = message.getText();
        int userId = message.getFrom().getId();
        BotState botState = BotState.ASK_START;
        SendMessage replyMessage = null;

        switch (inputMsg) {
            case "/start":
                botState = BotState.ASK_START;
                break;
            case "/ad":
                botState = BotState.ASK_AD;
                System.out.println("ad added");
                break;
            default:
                botState = userAdCache.getUsersCurrentBotState(userId);
                break;
        }

        userAdCache.setUserCurrentBotState(userId, botState);

        replyMessage = botStateContext.processInputMessage(botState, message);

        return replyMessage;
    }
}
