package com.kitchen.orders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
@RestController
@RequestMapping("/orders")
public class OrderController {
 private final RestaurantOrderRepository orderRepository;
 public OrderController(RestaurantOrderRepository orderRepository) { this.orderRepository = orderRepository; }

 @GetMapping public List<RestaurantOrder> getAll() { return orderRepository.findAll(); }
 @GetMapping("/{id}") public RestaurantOrder getById(@PathVariable Long id) {
   return orderRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "RestaurantOrder not found"));
 }
 @PostMapping @ResponseStatus(HttpStatus.CREATED) public RestaurantOrder create(@RequestBody RestaurantOrder item) {
   item.setId(null); return orderRepository.save(item);
 }
 @PutMapping("/{id}") public RestaurantOrder update(@PathVariable Long id, @RequestBody RestaurantOrder item) {
   if (!orderRepository.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "RestaurantOrder not found");
   item.setId(id); return orderRepository.save(item);
 }
 @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) {
   if (!orderRepository.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "RestaurantOrder not found");
   orderRepository.deleteById(id);
 }
}
