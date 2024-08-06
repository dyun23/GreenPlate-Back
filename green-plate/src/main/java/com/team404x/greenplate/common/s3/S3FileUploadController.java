package com.team404x.greenplate.common.s3;

import com.team404x.greenplate.common.BaseResponse;
import com.team404x.greenplate.common.BaseResponseMessage;
import com.team404x.greenplate.config.SecuredOperation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class S3FileUploadController {
    private final S3FileUploadSevice S3FileUploadSevice;
    private final S3FileUploadSevice s3FileUploadSevice;

    @SecuredOperation
    @Operation(summary = "[유저/사업자] 레시피 등록 API")
    @PostMapping(value = "/upload")
    public BaseResponse<String> create(@RequestPart(value = "image") MultipartFile image) {
        return new BaseResponse<>(BaseResponseMessage.IMAGE_UPLOAD_SUCCESS, s3FileUploadSevice.upload(image));
    }
}
