package com.eager.core.freemarker;

import java.io.PrintWriter;
import java.io.Writer;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

/**
 * @ClassName: TianqueTemplateExceptionHandler
 * @Description: TODO(模板异常统一报错页面)
 * @author yumeng
 * @date 2013-12-19 上午09:16:12
 * 
 */
public class EagerTemplateExceptionHandler implements
		TemplateExceptionHandler {
	private static final String ERROR_IFNO = "<script language=javascript>$(function(){"
			+ "$('body').append(\"<div style='background: rgb(214, 214, 214);position:absolute;left: 210px;top: 126px;right: 5px;bottom: inherit;font-size: 16px;color: #5C5C5C;text-align: center;line-height: 9em;'> 页面出错 啦，如果刷新页面无法解决，请联系运维人员。</div>\")"
			+ "})</script>";

	@Override
	public void handleTemplateException(TemplateException te, Environment env,
			Writer out) {
		// 判断是否是<#attempt> 块的执行
		if (!env.isInAttemptBlock()) {
			boolean externalPw = out instanceof PrintWriter;
			PrintWriter pw = externalPw ? (PrintWriter) out : new PrintWriter(
					out);
			try {
				pw.print(ERROR_IFNO);

				pw.flush();

			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				if (!externalPw)
					pw.close();
			}
		}

	}
}
