package Network.Message;

public class MessageContenitor {

    private final Object content;
    private int type;
    public static final int MV_EVENT=0;
    public static final int CV_EVENT=1;

    public MessageContenitor(int type, Object content){
        this.type=type;
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public Object getContent() {
        return content;
    }

}
