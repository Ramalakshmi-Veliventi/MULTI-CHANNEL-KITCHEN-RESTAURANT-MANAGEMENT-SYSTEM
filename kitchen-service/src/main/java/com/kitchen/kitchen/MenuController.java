package com.kitchen.kitchen;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
@RestController
@RequestMapping("/kitchen/menu")
public class MenuController {
 private final MenuItemRepository menuItemRepository;
 public MenuController(MenuItemRepository menuItemRepository) { this.menuItemRepository = menuItemRepository; }

 @GetMapping public List<MenuItem> getAll() { return menuItemRepository.findAll(); }
 @GetMapping("/{id}") public MenuItem getById(@PathVariable Long id) {
   return menuItemRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "MenuItem not found"));
 }
 @PostMapping @ResponseStatus(HttpStatus.CREATED) public MenuItem create(@RequestBody MenuItem item) {
   item.setId(null); return menuItemRepository.save(item);
 }
 @PutMapping("/{id}") public MenuItem update(@PathVariable Long id, @RequestBody MenuItem item) {
   if (!menuItemRepository.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "MenuItem not found");
   item.setId(id); return menuItemRepository.save(item);
 }
 @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) {
   if (!menuItemRepository.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "MenuItem not found");
   menuItemRepository.deleteById(id);
 }
}
