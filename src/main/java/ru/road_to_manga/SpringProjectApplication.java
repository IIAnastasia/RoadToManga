package ru.road_to_manga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringProjectApplication.class, args);
//        Manager manager = new Manager();
        System.out.println("HI");
    }

}

//public class Main {
//
//    public static void main(String[] args) throws IOException, ClientException, ApiException {
//        TransportClient transportClient = new HttpTransportClient();
//        VkApiClient vk = new VkApiClient(transportClient);
//        System.out.println("Hello world!");
//        Properties property = new Properties();
//        FileInputStream fis = new FileInputStream("src/main/resources/application.properties");
//        property.load(fis);
//        Integer GOD_ID = Integer.valueOf(property.getProperty("GOD_ID"));
//        String GOD_ACCESS = property.getProperty("GOD_ACCESS");
//        UserActor actor = new UserActor(GOD_ID, GOD_ACCESS);
//        MainDB db = new MainDB();
//        Updater updater = new Updater(vk, actor, db);
//        updater.update("");
//    }
//}