package br.com.fiap.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import br.com.fiap.model.ItemModel;
import br.com.fiap.repository.CategoriaRepository;
import br.com.fiap.repository.ItemRepository;

@RestController
@RequestMapping("/item")
public class ItemController {
	@Autowired
	public ItemRepository itemRepository;

	@Autowired
	public CategoriaRepository categoriaRepository;

	@GetMapping()
	public ResponseEntity<List<ItemModel>> findAll(Model model) {

		List<ItemModel> itens = itemRepository.findAll();
		return ResponseEntity.ok(itens);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ItemModel> findById(@PathVariable("id") long id) {

		ItemModel itens = itemRepository.findById(id).get();
		return ResponseEntity.ok(itens);
	}

	@PostMapping()
	public ResponseEntity save(@RequestBody @Valid ItemModel itemModel, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().build();
		}

		ItemModel item = itemRepository.save(itemModel);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(item.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}
	@PutMapping("/{id}")
	public ResponseEntity update(@PathVariable("id") long id, @RequestBody @Valid ItemModel itemModel, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().build();
		}
		
		itemModel.setId(id);
		itemRepository.save(itemModel);
//		produtoRepository.updateProductNameAndSku(produtoModel.getNome(), produtoModel.getSku(), produtoModel.getId());
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity deleteById(@PathVariable("id") long id) {
		
		itemRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}