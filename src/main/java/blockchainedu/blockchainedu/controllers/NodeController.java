package blockchainedu.blockchainedu.controllers;

import blockchainedu.blockchainedu.models.Blockchain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/nodes")
public class NodeController {

    @Autowired
    private Blockchain blockchain;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Object> registerNodes(@RequestBody List<String> nodes) {
        if (nodes == null || nodes.size() == 0) {
            return new ResponseEntity<Object>("Error: Please supply a valid list of nodes", HttpStatus.BAD_REQUEST);
        }

        for (String node : nodes) {
            try {
                blockchain.registerNode(new URL(node));
            } catch (MalformedURLException e) {
                return new ResponseEntity<Object>("Error: Invalid node " + node + ", Please supply a valid node", HttpStatus.BAD_REQUEST);
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("message", "New nodes have been added");
        response.put("total_nodes", blockchain.getNodes());

        return new ResponseEntity<Object>(response, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ResponseEntity<Object> infoNodes() {

        Map<String, Object> response = new HashMap<>();
        Set<URL> nodes = blockchain.getNodes();
        for (URL node : nodes) {
            response.put("node", node);
        }

        return new ResponseEntity<Object>(response, HttpStatus.CREATED);
    }

    @RequestMapping("/sync")
    public Map<String, Object> consensus() {
        Boolean replaced = blockchain.syncNode();

        Map<String, Object> response = new HashMap<>();
        response.put("new_chain", blockchain.getChain());

        if (replaced) {
            response.put("message", "Our chain was replaced");
        } else {
            response.put("message", "Our chain is authoritative");
        }

        return response;
    }

}
