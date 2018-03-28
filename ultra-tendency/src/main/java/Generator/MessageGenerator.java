package Generator;

import java.util.Random;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.Data;
import model.Data.Location;
import model.Message;

public class MessageGenerator implements Runnable {

	private UUID uuid = UUID.randomUUID();
	private ObjectMapper mapper = new ObjectMapper();
	private Random rand = new Random();
	private Location loc = createRandomLocation();

	private Location createRandomLocation() {
		Location loc = new Location();

		double minLat = -90.00;
		double maxLat = 90.00;
		double latitude = minLat + (double) (Math.random() * ((maxLat - minLat) + 1));
		double minLon = 0.00;
		double maxLon = 180.00;
		double longitude = minLon + (double) (Math.random() * ((maxLon - minLon) + 1));

		loc.setLatitude(latitude);
		loc.setLongitude(longitude);
		return loc;
	}

	private Message nextSignal() {
		Data data = new Data();
		data.setDeviceId(uuid);
		data.setTime(System.currentTimeMillis());
		data.setTemperature(rand.nextInt(30));
		data.setLocation(loc);

		Message message = new Message();
		message.setData(data);
		return message;
	}

	public void run() {
		while (true) {
			Message message = nextSignal();
			try {
				String data = mapper.writeValueAsString(message);
				System.out.println(data);
				Thread.sleep(1000);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// TODO Auto-generated method stub

	}

}
