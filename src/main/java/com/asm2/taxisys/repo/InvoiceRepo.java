package com.asm2.taxisys.repo;

import com.asm2.taxisys.entity.Invoice;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InvoiceRepo extends PagingAndSortingRepository<Invoice, Long>, JpaSpecificationExecutor<Invoice> {
    Invoice findInvoiceById(Long id);
}
