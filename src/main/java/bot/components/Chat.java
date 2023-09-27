package bot.components;

public class Chat {
    private Long chat_id;
    private String type;

    public Chat(Long chat_id, String type) {
        this.chat_id = chat_id;
        this.type = type;
    }

    public Long getChat_id() {
        return chat_id;
    }

    public void setChat_id(Long chat_id) {
        this.chat_id = chat_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "chat_id=" + chat_id +
                ", type='" + type + '\'' +
                '}';
    }
}
