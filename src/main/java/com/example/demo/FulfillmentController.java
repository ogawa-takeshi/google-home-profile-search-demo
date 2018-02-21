package com.example.demo;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/fulfillment")
public class FulfillmentController {

	@Autowired
	private ProfileRepository profileRepository;

	@PostMapping
	public FulfillmentResponse fulfill(@RequestBody JsonNode request) {
		String name = request.get("queryResult").get("parameters").get("last-name").asText();
		String what = request.get("queryResult").get("parameters").get("what").asText();

		Optional<Profile> profile = profileRepository.findByName(name);
		if (!profile.isPresent()) {
			return new FulfillmentResponse("そんな人はいません");
		}

		switch (what) {
			case "誕生日":
				return new FulfillmentResponse(String.format("%sさんの誕生日は%sです", profile.get().getName(), profile.get().getBirthday()));
			case "血液型":
				return new FulfillmentResponse(String.format("%sさんの血液型は%s型です", profile.get().getName(), profile.get().getBloodType()));
			case "最近の様子":
				return new FulfillmentResponse(String.format("%sさんの最近の様子は%sです", profile.get().getName(), profile.get().getUpdates()));
			default:
				return new FulfillmentResponse("そのことについてはよく知りません");
		}
	}
}
