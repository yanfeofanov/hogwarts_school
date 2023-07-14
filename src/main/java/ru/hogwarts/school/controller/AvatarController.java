package ru.hogwarts.school.controller;

import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.service.AvatarService;

import java.util.List;

@RestController
@RequestMapping("/avatars")
public class AvatarController {

    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @GetMapping("/{id}/from-db")
    public ResponseEntity<byte[]> getFromDb(@PathVariable long id){
    return build(avatarService.getFromDb(id));
    }

    @GetMapping("/{id}/from-fs")
    public ResponseEntity<byte[]> getFromFs(@PathVariable long id){
    return build(avatarService.getFromFs(id));
    }

    private ResponseEntity<byte[]> build(Pair<byte[],String> stringPair){
        byte[] data = stringPair.getFirst();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(stringPair.getSecond()))
                .contentLength(data.length)
                .body(data);
    }

    @GetMapping("/avatars-list")
    public List<String> getAvatarsList(@RequestParam("number") Integer pageNumber, @RequestParam("size") Integer pageSize) {
        return avatarService.getAvatarsList(pageNumber, pageSize);
    }
}
