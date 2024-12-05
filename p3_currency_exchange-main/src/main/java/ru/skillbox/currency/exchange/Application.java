package ru.skillbox.currency.exchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.skillbox.currency.exchange.service.CurrencyService;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Application.class, args);
		CurrencyService service = context.getBean(CurrencyService.class);
		service.updateDatabase();
		new Thread(()-> {
			while (true) {
				try {
					Thread.sleep(60 * 60 * 1000); //min * sec * millis
					service.updateDatabase();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}).start();
	}
}
