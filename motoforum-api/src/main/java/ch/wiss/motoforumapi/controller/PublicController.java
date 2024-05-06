package ch.wiss.motoforumapi.controller;

import java.io.Serializable;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicController {

    class DemoData implements Serializable {
        String key;
        String value;

        public DemoData(String key, String value) {
            this.key = key;
            this.value = value;
        }
        public void setKey(String key) {
            this.key = key;
        }
        public void setVaue(String value) {
            this.value = value;
        }
        public String getKey() {
            return key;
        }
        public String getVaue() {
            return value;
        }
    }

    @GetMapping("/public")
    public ResponseEntity<String> getPublicPart() {
        return ResponseEntity.ok("Dies ist der Public Teil ");
    }

    @GetMapping("/public/items")
    public ResponseEntity<List<DemoData>> getPublicDemoData(){

        List <DemoData> data = List.of(
            new DemoData("key1","data1"),
            new DemoData("key2","data2"),
            new DemoData("key3","data3"));
            return ResponseEntity.ok().body(data);
    }
}
