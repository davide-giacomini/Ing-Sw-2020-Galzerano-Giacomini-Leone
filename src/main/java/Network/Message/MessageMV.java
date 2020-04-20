package Network.Message;

import View.View;
import java.io.Serializable;

public interface MessageMV extends Serializable {

    void accept(View view);

}
