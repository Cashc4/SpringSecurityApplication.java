package com.example.springsecurityapplication.controllers;

import com.example.springsecurityapplication.enumm.Status;
import com.example.springsecurityapplication.models.Image;
import com.example.springsecurityapplication.models.Order;
import com.example.springsecurityapplication.models.Person;
import com.example.springsecurityapplication.models.Product;
import com.example.springsecurityapplication.repositories.CategoryRepository;
import com.example.springsecurityapplication.repositories.OrderRepository;
import com.example.springsecurityapplication.repositories.PersonRepository;
import com.example.springsecurityapplication.security.PersonDetails;
import com.example.springsecurityapplication.services.OrderService;
import com.example.springsecurityapplication.services.PersonService;
import com.example.springsecurityapplication.services.ProductService;
import com.example.springsecurityapplication.util.PersonValidator;
import com.example.springsecurityapplication.util.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
//@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
public class AdminController {

    @Value("${upload.path}")
    private String uploadPath;

    private final ProductValidator productValidator;
    private final ProductService productService;
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final CategoryRepository categoryRepository;
    private final PersonValidator personValidator;
    private final PersonService personService;
    private final PersonRepository personRepository;

    @Autowired
    public AdminController(ProductValidator productValidator, ProductService productService, OrderService orderService, OrderRepository orderRepository, CategoryRepository categoryRepository, PersonValidator personValidator, PersonService personService, PersonRepository personRepository) {
        this.productValidator = productValidator;
        this.productService = productService;
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.categoryRepository = categoryRepository;
        this.personValidator = personValidator;
        this.personService = personService;
        this.personRepository = personRepository;
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN') and hasRole('')")
//@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('')")

    // Метод по отображению главной страницы администратора с выводом товаров
    @GetMapping()
    public String admin(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        String role = personDetails.getPerson().getRole();

        if (role.equals("ROLE_USER")) {
            return "redirect:/index";
        }
        if (role.equals("ROLE_SELLER")) {
            return "redirect:/seller";
        }
        model.addAttribute("products", productService.getAllProduct());
        return "admin/admin";
    }

    ///////////////////Продукты////////////
    // Метод по отображению формы добавление
    @GetMapping("/product/add")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("category", categoryRepository.findAll());
//        System.out.println(categoryRepository.findAll().size());
        return "product/addProduct";
    }

    // Метод по добавлению объекта с формы в таблицу product
    @PostMapping("/product/add")
    public String addProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, @RequestParam("file_one") MultipartFile file_one, @RequestParam("file_two") MultipartFile file_two, @RequestParam("file_three") MultipartFile file_three, @RequestParam("file_four") MultipartFile file_four, @RequestParam("file_five") MultipartFile file_five, Model model) throws IOException {

        productValidator.validate(product, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("category", categoryRepository.findAll());
            return "product/addProduct";
        }
        // Проверка на пустоту файла
        if (file_one != null) {
            // Дирректория по сохранению файла
            File uploadDir = new File(uploadPath);
            // Если данной дирректории по пути не сущетсвует
            if (!uploadDir.exists()) {
                // Создаем данную дирректорию
                uploadDir.mkdir();
            }
            // Создаем уникальное имя файла
            // UUID представляет неищменный универсальный уникальный идентификатор
            String uuidFile = UUID.randomUUID().toString();
            // file_one.getOriginalFilename() - наименование файла с формы
            String resultFileName = uuidFile + "." + file_one.getOriginalFilename();
            // Загружаем файл по указаннопу пути
            file_one.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageProduct(image);
        }

        // Проверка на пустоту файла
        if (file_two != null) {
            // Дирректория по сохранению файла
            File uploadDir = new File(uploadPath);
            // Если данной дирректории по пути не сущетсвует
            if (!uploadDir.exists()) {
                // Создаем данную дирректорию
                uploadDir.mkdir();
            }
            // Создаем уникальное имя файла
            // UUID представляет неищменный универсальный уникальный идентификатор
            String uuidFile = UUID.randomUUID().toString();
            // file_one.getOriginalFilename() - наименование файла с формы
            String resultFileName = uuidFile + "." + file_two.getOriginalFilename();
            // Загружаем файл по указаннопу пути
            file_two.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageProduct(image);
        }

        // Проверка на пустоту файла
        if (file_three != null) {
            // Дирректория по сохранению файла
            File uploadDir = new File(uploadPath);
            // Если данной дирректории по пути не сущетсвует
            if (!uploadDir.exists()) {
                // Создаем данную дирректорию
                uploadDir.mkdir();
            }
            // Создаем уникальное имя файла
            // UUID представляет неищменный универсальный уникальный идентификатор
            String uuidFile = UUID.randomUUID().toString();
            // file_one.getOriginalFilename() - наименование файла с формы
            String resultFileName = uuidFile + "." + file_three.getOriginalFilename();
            // Загружаем файл по указаннопу пути
            file_three.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageProduct(image);
        }

        // Проверка на пустоту файла
        if (file_four != null) {
            // Дирректория по сохранению файла
            File uploadDir = new File(uploadPath);
            // Если данной дирректории по пути не сущетсвует
            if (!uploadDir.exists()) {
                // Создаем данную дирректорию
                uploadDir.mkdir();
            }
            // Создаем уникальное имя файла
            // UUID представляет неищменный универсальный уникальный идентификатор
            String uuidFile = UUID.randomUUID().toString();
            // file_one.getOriginalFilename() - наименование файла с формы
            String resultFileName = uuidFile + "." + file_four.getOriginalFilename();
            // Загружаем файл по указаннопу пути
            file_four.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageProduct(image);
        }

        // Проверка на пустоту файла
        if (file_five != null) {
            // Дирректория по сохранению файла
            File uploadDir = new File(uploadPath);
            // Если данной дирректории по пути не сущетсвует
            if (!uploadDir.exists()) {
                // Создаем данную дирректорию
                uploadDir.mkdir();
            }
            // Создаем уникальное имя файла
            // UUID представляет неищменный универсальный уникальный идентификатор
            String uuidFile = UUID.randomUUID().toString();
            // file_one.getOriginalFilename() - наименование файла с формы
            String resultFileName = uuidFile + "." + file_five.getOriginalFilename();
            // Загружаем файл по указаннопу пути
            file_five.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageProduct(image);
        }

        productService.saveProduct(product);
        return "redirect:/admin";
    }

    // Метод по удалению товара по id
    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id) {
        productService.deleteProduct(id);
        return "redirect:/admin";
    }

    // Метод по получению товара по id и отображение шаблона редактирования
    @GetMapping("/product/edit/{id}")
    public String editProduct(@PathVariable("id") int id, Model model) {
        model.addAttribute("editProduct", productService.getProductId(id));
        model.addAttribute("category", categoryRepository.findAll());
        return "product/editProduct";
    }

    @PostMapping("/product/edit/{id}")
    public String editProduct(@ModelAttribute("editProduct") @Valid Product product, BindingResult bindingResult, @PathVariable("id") int id, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("category", categoryRepository.findAll());
            return "product/editProduct";
        }

        productService.updateProduct(id, product);
        return "redirect:/admin";
    }


    ////////////////Пользователи/////////////////////

    // Метод возвращает страницу с выводом пользователей и кладет объект пользователя в модель
    @GetMapping("/person")
    public String person(Model model) {
        ;
        model.addAttribute("person", personService.getAllPerson());
        return "person/person";
    }

    @GetMapping("/person/add")
    public String getFormAddUser(Model model) {
        model.addAttribute("person", new Person());
        return "admin/addPerson";

    }

    //Обработка формы добавления и сохранения объекта с формы
    @PostMapping("/add")
    public String addUser(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, Model model) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {


            return "admin/addPerson";
        }


        personService.savePerson(person);
        return "redirect:/admin/person";
    }


    // Метод возвращает страницу с подробной информацией о пользователе
    @GetMapping("/person/info/{id}")
    public String infoPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personService.getPersonById(id));
        return "person/personInfo";
    }


    // Метод возвращает страницу с формой редактирования пользователя и помещает в модель объект редактируемого пользователя по id
    @GetMapping("/person/edit/{id}")
    public String editPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("editPerson", personService.getPersonById(id));
        return "person/editPerson";
    }

    // Метод принимает объект с формы и обновляет пользователя
    @PostMapping("/person/edit/{id}")
    public String editPerson(@ModelAttribute("editPerson") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id, Model model) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("person", personService.getAllPerson());
            return "person/editPerson";


        }
        personService.updatePerson(id, person);
        return "redirect:/admin/person";
    }


    // Метод по удалению пользователей
    @GetMapping("/person/delete/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        personService.deletePerson(id);
        return "redirect:/admin/person";
    }

    // Метод по нажатию на кнопку поиска и сортировки и отображение шаблона
    @GetMapping("/person/sorting_and_searching_and_filters")
    public String sorting_and_searching_and_filters() {
        return "/person/SortingAndSearchingAndFilters";
    }

    @PostMapping("/person/sorting_and_searсhing_and_filters")
    public String sorting_and_searching_and_filters(@RequestParam("SortingAndSearchingAndFiltersOptions") String sortingAndSearchingAndFiltersOptions, @RequestParam("value") String value, Model model) {
        if (sortingAndSearchingAndFiltersOptions.equals("email")) {
            model.addAttribute("person", personService.getPersonEmail(value));
        } else if (sortingAndSearchingAndFiltersOptions.equals("phone_number")) {
            model.addAttribute("person", personService.getPersonPhoneNumber(value));
        } else if (sortingAndSearchingAndFiltersOptions.equals("last_name_and_sort_birthday")) {
            model.addAttribute("person", personService.getPersonLastNameOrderByBirthday(value));
        } else if (sortingAndSearchingAndFiltersOptions.equals("last_name_start")) {
            model.addAttribute("person", personService.getPersonLastNameStartingWith(value));
        }
        return "person/SortingAndSearchingAndFilters";
    }


    ////////////////Заказы/////////////////////


    @GetMapping("/orderList")
    public String showOrders(Model model) {
        // model.addAttribute("person", new Person());

        model.addAttribute("orders", orderRepository.findAll());

        return "admin/orderList";
    }

    @GetMapping("/orderList/search")
       public String orderSearch(Model model) {
            return "admin/orderList";
}
    @PostMapping("/orderList/search")
    public String orderSearch(@RequestParam("search") String search, Model model) {

//        if (search.length() > 4) {
//            search = search.substring(search.length() - 4);
//        }
//        model.addAttribute("search_order", orderRepository.findByLastFourCharacters(search));
//        model.addAttribute("value_search", search);

//        System.out.println(search);

//            model.addAttribute("value_search", search);
//        System.out.println(search);
//            model.addAttribute("orders", orderService.getAllOrder());

        model.addAttribute("search_order", orderRepository.findByNumberLikeIgnoreCase("%" + "%"));
        model.addAttribute("value_search", search);
        model.addAttribute("orders", orderService.getAllOrder());

//        System.out.println("Метод");
//        model.addAttribute("search_order", orderRepository.findByNumberContainingIgnoreCase(search));
//        System.out.println(search);
//        model.addAttribute("value_search", search);
//        model.addAttribute("orders", orderRepository.findAll());
//
//        if(search.length()>4) {
//            search = search.substring(search.length() - 4);
//        }
//
//        System.out.println(search);

        return "admin/orderList";
        }


    // Метод по удалению ордера по id
    @GetMapping("/orderList/delete/{id}")
    public String deleteOrder(@PathVariable("id") int id){
       orderService.deleteOrder(id);
        return "redirect:/admin/orderList";
    }


    // Метод по получению ордера по id и отображение шаблона редактирования
    @GetMapping("/orderList/edit/{id}")
    public String editOrder(@PathVariable("id") int id, Model model){
        model.addAttribute("editOrder", orderService.getOrderId(id));

        return "admin/editOrder";
    }

    @PostMapping("/orderList/edit/{id}")
    public String editOrderCancel(@ModelAttribute("editOrder") Order order,  @PathVariable("id") int id){
        System.out.println("Отменён");
           orderService.updateOrder(id,order);
            return "redirect:/admin/orderList";
        }

}



