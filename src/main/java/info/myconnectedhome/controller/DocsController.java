package info.myconnectedhome.controller;

import java.nio.charset.Charset;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import info.myconnectedhome.model.Docs;
import info.myconnectedhome.repository.DocsRepository;

@RestController
@RequestMapping("/Document")
public class DocsController {
	Logger logger = Logger.getLogger(DocsController.class);
	Gson gson = new Gson();
	@Autowired
	DocsRepository repo;

	@Autowired
	private AmqpTemplate amqpTemplate;
	private static final Charset CHARSET_UTF8 = Charset.forName("UTF-8");
	private static final String SEARCH_QUEUE = "SEARCH.QUEUE";
	
	public DocsController(AmqpAdmin amqpAdmin) {
		amqpAdmin.declareQueue(new Queue(SEARCH_QUEUE));
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Docs save(@RequestBody Docs doc) {
		repo.save(doc);
		amqpTemplate.convertAndSend(SEARCH_QUEUE, gson.toJson(doc).toString());
		logger.debug("Enqueued the document into RabbitMQ");
		return doc;
	}
	

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<Docs> getAllDocument() {
		return repo.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Docs getDocument(@PathVariable("id") int id) {
		return repo.findOne(id);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<Docs> getDocumentBydocumentName(@RequestParam("documentName") String documentName) {
		return repo.findBydocumentName(documentName);
	}
}
