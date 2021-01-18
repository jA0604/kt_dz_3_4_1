import models.Chat
import models.Message
import services.ChatService
import services.DaoService

fun main() {

}

public val Chat.lastReadMessage: Int
    get() = this.getMessages().size - getMessages().filter { it.isRead }.size - 1

public val ChatService.unreadChatCount: Int
    get() = this.getChats().map { messageList ->  messageList.getMessages().filter { !it.isRead } }.size

public fun ChatService.getChatsWithLastMessage() = this.getChats().filter { it.lastReadMessage > -1 }

public fun ChatService.getChatCountMessage(idContact: Int, idLastMessage: Int, countMessages: Int): List<Message>
 {
    return (this
            .getChatById(idContact)
            .getMessages().asSequence()
            .filter { it.id > idLastMessage  }
            .take(countMessages)
            .map { m -> m.apply { isRead = true} }
            .toList()
            ) ?: emptyList()

}

