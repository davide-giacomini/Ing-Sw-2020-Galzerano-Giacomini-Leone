package Network.Message;

import java.io.Serializable;

public class MessageContainer implements Serializable {

    private final Object content;
    private int type;
    public static final int MV_EVENT=0;
    public static final int CV_EVENT=1;

    public MessageContainer(int type, Object content){
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
