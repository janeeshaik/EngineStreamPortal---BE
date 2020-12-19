package com.ste.enginestreamportal.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ste.enginestreamportal.model.Pages;
import com.ste.enginestreamportal.repository.PagesRepository;
import com.ste.enginestreamportal.services.PageService;
import com.ste.enginestreamportal.util.Response;
import com.ste.enginestreamportal.util.Utils;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/Page")
@ResponseBody
public class PageController extends Utils{

	@Autowired
	PagesRepository pagesRepository;

	@Autowired
	PageService pageService;

	private Logger logger = LogManager.getLogger(PageController.class);

	@GetMapping("/pagination")
    public Response getAllPagesPagination(@RequestParam("showAll") Integer showAll, @RequestParam("pageIndex") int pageIndex,
            @RequestParam("sortType") String sortType, @RequestParam("searchWord") String searchWord,@RequestParam("statusType") String statusType) {
       
        Response response = new Response();
        Pageable pageable = null;
        Page<Pages> page = null;
        try {
            response.setStatus(true);
            response.setError(null);
            if(sortType != null && sortType.equalsIgnoreCase("asc")) {
                pageable = PageRequest.of(pageIndex, showAll, Sort.by("pageName").ascending());
            } else {
                pageable = PageRequest.of(pageIndex, showAll, Sort.by("pageName").descending());
            }
            page = pageService.findAllPages(pageable,sortType,searchWord,statusType);
            response.setResponse(page);
        } catch(Exception e) {
            response.setStatus(false);
            response.setError("Unable to get all Pages");
            logger.error("Error : "+e);
            response.setResponse(null);
        }
        return response;
    }

	@GetMapping("/{Id}")
	public Response getAllPagesById(@PathVariable(value = "Id") Long id) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			logger.debug(String.format("Fetching role %d.", id));
			Optional<Pages> page = pagesRepository.findById(id);
			response.setResponse(page.get());
			response.setStatus(true);
			response.setError(null);
		}
		catch(NoSuchElementException ex) {
			response.setError("Unable to find Page with id :"+id);
			logger.error("Error : "+ex);
		}
		catch(Exception e) {
			response.setError("Unable to get Page");
			logger.error("Error : "+e);
		}

		return response;
	}

	@PostMapping("/")
	public Response createPageName(@Valid @RequestBody Pages pages) {
		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			if(null == pages) {
				response.setError("Payload cannot be null or empty");
			}else if(isNullOrEmpty(pages.getPageName())) {
				response.setError("PageName cannot be null or empty");
			}
			else if(isNullOrEmpty(pages.getPageNavigation())) {
				response.setError("Page Navigation cannot be null or empty");
			}
			else {
				Pages createPageName = pagesRepository.save(pages);
				response.setStatus(true);
				response.setError(null);
				response.setResponse(createPageName);
			}
		} catch (Exception e) {
			response.setError("Could not create page");
			logger.error("Error : "+e);
		}
		return response;
	}


	@PutMapping("/{Id}")
	public Response updatePages(@PathVariable(value = "Id") Long id, @Valid @RequestBody Pages page) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			Optional<Pages> optPage = pagesRepository.findById(id);
			if (!optPage.isPresent()) {
				response.setError("Could not find a page with ID = " + id);
				return response;
			}
			if(isNullOrEmpty(page.getPageName())) {
				response.setError("PageName cannot be null or empty");
			}
			else if(isNullOrEmpty(page.getPageNavigation())) {
				response.setError("Page Navigation cannot be null or empty");
			}
			else {
				Pages pageToUpdate = optPage.get();

				pageToUpdate.setPageName(page.getPageName());
				pageToUpdate.setPageNavigation(page.getPageNavigation());
				pageToUpdate.setParentId(page.getParentId());
				pageToUpdate.setUpdatedBy(page.getUpdatedBy());
				pageToUpdate.setStatus(page.getStatus());
				pagesRepository.save(pageToUpdate);
				response.setStatus(true);
				response.setError(null);
				response.setResponse(pageToUpdate);
			}
		}catch(Exception e) {
			response.setError("Unable to update Page");
			logger.error("Error : "+e);
		}
		return response;
	}

	@GetMapping("/getParentPage")
	public Response getParentPage() {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			List<Object> pagedetails = pagesRepository.getParentPageDetails();
			response.setStatus(true);
			response.setError(null);
			response.setResponse(pagedetails);
		} catch(Exception e) {
			response.setError("Unable to get Parent Pages");
			logger.error("Error : "+e);
		}
		return response;
	}

	@GetMapping("/getParentPagedetails")
	public Response getParentPage(@RequestParam(value = "roleid") Long roleid) {

		Response response = new Response();
		response.setStatus(false);
		response.setResponse(null);
		try {
			List<Object> pagedetails = pagesRepository.getParentPageDetailswithroleidonly(roleid);
			response.setStatus(true);
			response.setError(null);
			response.setResponse(pagedetails);
		} catch(Exception e) {
			response.setError("Unable to get Parent Pages with role Id");
			logger.error("Error : "+e);
		}
		return response;
	}

	@PutMapping("/{Id}/{Status}")
	public Response updatePageStatus(@PathVariable(value = "Id") Long Id, @PathVariable(value="Status") Boolean status) {
		Response response = new Response();
		try {
			Pages pages = pagesRepository.findById(Id).get();
			pages.setStatus(status);
			pagesRepository.save(pages);
			response.setStatus(true);
			response.setError(null);
			response.setResponse(pages);
		} catch(NoSuchElementException ex) {
			response.setStatus(false);
			response.setError("Unable to find pages with id :"+Id);
			logger.error("Error : "+ex);
			response.setResponse(null);
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to update pages");
			logger.error("Error : "+e);
			response.setResponse(null);
		}
		return response;
	}
	
	@GetMapping("/")
	public Response getAllPages() {
		Response response = new Response();
		try {
			response.setStatus(true);
			response.setError(null);
			response.setResponse(pagesRepository.findAll());
		} catch(Exception e) {
			response.setStatus(false);
			response.setError("Unable to get all Pages");
			logger.error("Error : "+e);
			response.setResponse(null);
		}

		return response;
	}

}
