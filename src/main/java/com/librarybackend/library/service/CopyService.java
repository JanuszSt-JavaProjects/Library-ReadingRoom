package com.librarybackend.library.service;


import com.librarybackend.library.domain.Copy;
import com.librarybackend.library.domain.Status;
import com.librarybackend.library.domain.dto.copyDto.CopyStatusChangeDto;
import com.librarybackend.library.exception.copyException.NoSuchCopyException;
import com.librarybackend.library.exception.copyException.UnmodifiableStateException;
import com.librarybackend.library.repository.CopyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CopyService {

    CopyRepository copyRepository;

    public CopyService(CopyRepository copyRepository) {
        this.copyRepository = copyRepository;
    }

    public Copy save(Copy copy) {
        return copyRepository.save(copy);
    }

    public void delete(long Id) {
        Copy copy = copyRepository.findById(Id).orElseThrow(NoSuchCopyException::new);
        copyRepository.delete(copy);
    }

    public Copy update(Copy copy) {
        Copy updatedCopy = copyRepository.findById((long) copy.getId()).orElseThrow(NoSuchCopyException::new);

        Optional.ofNullable(copy.getSignature()).ifPresent(updatedCopy::setSignature);
        Optional.ofNullable(copy.getStatus()).ifPresent(updatedCopy::setStatus);
        copyRepository.save(updatedCopy);

        return updatedCopy;
    }

    public Copy get(long copyId) {
        System.out.println(copyId);
        return copyRepository.findById(copyId).orElseThrow(NoSuchCopyException::new);
    }

    public List<Copy> getAll() {
        return copyRepository.findAll();
    }

    public CopyStatusChangeDto setStatus(long id, Status status) {
        Copy copy = copyRepository.findById(id).orElseThrow(NoSuchCopyException::new);
        Status actualStatus = copy.getStatus();

        switch (actualStatus) {
            case LOST:
            case DESTROYED:
                throw new UnmodifiableStateException(actualStatus);
        }

        copy.setStatus(status);
        copyRepository.save(copy);
        return new CopyStatusChangeDto(id, status);
    }
}
