package com.project.movice.utils;

import android.content.Context;

import java.io.File;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by wy on 2018/1/30 12:14.
 */


/**
 * 图片压缩
 */
public class LubanUtil {
    public interface OnLubanFinishListener {
        public void finish(File file);

        public void error(Throwable e);
    }

    public static void getFile(Context c, String path, String targetDir, final OnLubanFinishListener listener) {
        Luban.with(c).load(path).ignoreBy(200).setTargetDir(targetDir)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(File file) {
                        if (null != listener) {
                            listener.finish(file);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (null != listener) {
                            listener.error(e);
                        }
                    }
                }).launch();
    }

}
