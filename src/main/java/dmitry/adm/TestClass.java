package dmitry.adm;

import org.springframework.context.annotation.Profile;

@Profile("dev")
public class TestClass {

    public void make() {
        System.out.println("sdfg");
    }

}
