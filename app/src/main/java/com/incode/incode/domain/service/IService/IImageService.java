package com.incode.incode.domain.service.IService;

import com.incode.incode.domain.service.callback.ImageServiceCallback;
import com.incode.incode.model.Image;

import java.util.List;

/**
 * Created by kuri on 6/3/17.
 */

public interface IImageService {
    List<Image> getAllImages(ImageServiceCallback callback);
}
