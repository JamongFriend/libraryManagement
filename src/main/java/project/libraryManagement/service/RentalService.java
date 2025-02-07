package project.libraryManagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.libraryManagement.domain.Rental;
import project.libraryManagement.repository.RentalRepository;

@Service
@RequiredArgsConstructor
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;

    public void rentalBook() {

    }

    public void cancel() {

    }

    public void findRentaled() {

    }

    public void findAll() {

    }
}
