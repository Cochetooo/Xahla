package {{ package }};

import xahla.controllers.Controller;
import static xahla.views.View.*;

public class {{ objectName }}Controller extends Controller {
    
    public View show({{ objectName }} elem) {
        return view("{{ objectName }}.show", elem);
    }

    public View index() {
        List<{{ objectName }}> elems = {{ objectName }}.all();
        return view("{{ objectName }}.index", elems);
    }

}
