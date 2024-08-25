package myApp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import myApp.entity.BusTicket;
import myApp.util.Constants;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class BusTicketService {

    public BusTicketService(){}

    public List<BusTicket> loadBusTickets() {
        List<BusTicket> ticketsList = new ArrayList<>();
        Resource resource = new ClassPathResource(Constants.FILE_PATH);

        try (InputStream inputStream = resource.getInputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            ticketsList = objectMapper.readValue(inputStream, new TypeReference<List<BusTicket>>() {});
        } catch (JsonProcessingException e) {
            System.err.println("Error processing JSON: " + e.getMessage());
            return null;
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Unexpected error reading file: " + e.getMessage());
            return null;
        }

        return ticketsList;
    }
}