package botapi.handlers.askad;

import botapi.BotState;
import botapi.InputMessageHandler;
import cache.UserAdCache;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class AskAdHandler implements InputMessageHandler {
    private UserAdCache userAdCache;

    public AskAdHandler(UserAdCache userAdCache){
        this.userAdCache = userAdCache;
    }


    @Override
    public SendMessage handle(Update update) {
        return processUsersInput(update);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.ASK_START;
    }

    private SendMessage processUsersInput(Update update) {
        Message inputMsg = update.getMessage();
        int userId = inputMsg.getFrom().getId();
        long chatId = inputMsg.getChatId();

        SendMessage replyToUser = new SendMessage(chatId,
                "Бот для размещения объявлений");
        userAdCache.setUserCurrentBotState(userId,BotState.ASK_START);

        return replyToUser;
    }
}
