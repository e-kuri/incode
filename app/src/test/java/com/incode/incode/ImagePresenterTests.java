package com.incode.incode;


import android.content.Context;

import com.incode.incode.domain.ImageAPI;
import com.incode.incode.domain.ImageListAdapter;
import com.incode.incode.domain.ImageRequester;
import com.incode.incode.model.Image;
import com.incode.incode.presentation.presenter.IPresenter.ImageViewerContract;
import com.incode.incode.presentation.presenter.ImageViewPresenter;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.mock.Calls;

public class ImagePresenterTests {

    private static final int IMAGES_QTY = 5;

    @Mock
    private ImageViewerContract.View mView;

    @Mock
    private ImageAPI mImageApi;

    @Mock
    private Context mContext;

    @Mock
    private ImageRequester mImageRequester;

    @Mock
    private ImageViewPresenter mPresenter;
    private Call<List<Image>> successImagesCall;


    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setup(){
        final List<Image> imagesList = getMockImages(IMAGES_QTY);
        successImagesCall = Calls.response(imagesList);
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((Callback<List<Image>>)invocation.getArgument(0)).onResponse(successImagesCall,
                        Response.success(imagesList));
                return null;
            }
        }).when(mImageRequester).requestImages(Mockito.any(Callback.class));
        mPresenter = new ImageViewPresenter(mView, mImageRequester);
    }

    @Test
    public void TestGetMockImages(){

        Mockito.when(mImageApi.getImages()).thenReturn(successImagesCall);
        Callback requestCallback = new Callback<List<Image>>() {

            @Override
            public void onResponse(Call<List<Image>> call, Response<List<Image>> response) {
                Assert.assertEquals(response.body().size(), IMAGES_QTY);
            }

            @Override
            public void onFailure(Call<List<Image>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        };

        mPresenter.getServerImages(requestCallback);

    }

    private List<Image> getMockImages(int qty){
        List<Image> images = new ArrayList<>();
        for(int i=0; i<qty; i++){
            images.add(Mockito.mock(Image.class));
        }
        return images;
    }

}
