package net.codejava.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import ch.ehi.ili2db.base.Ili2db;
import ch.ehi.ili2db.base.Ili2dbException;
import ch.ehi.ili2db.gui.Config;
import ch.ehi.ili2pg.converter.PostgisGeometryConverter;
import ch.ehi.sqlgen.generator_impl.jdbc.GeneratorPostgresql;


@WebServlet("/UploadServlet")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB 
                 maxFileSize=1024*1024*20,      // 20MB
                 maxRequestSize=1024*1024*50)   // 50MB
public class UploadServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    /**
     * Name of the directory where uploaded files will be saved, relative to
     * the web application directory.
     */
    private static final String SAVE_DIR = "uploadFiles";
       
    public UploadServlet() {
        super();
    }

    /**
     * handles file upload
     */    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// gets absolute path of the web application
        String appPath = request.getServletContext().getRealPath("");
        System.out.println(appPath);
        // constructs path of the directory to save uploaded file
        String savePath = appPath + File.separator + SAVE_DIR;
         
        // creates the save directory if it does not exists
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }
         
        String savedFileName = null;
        for (Part part : request.getParts()) {
            String fileName = extractFileName(part);
            savedFileName = savePath + File.separator + fileName;
            part.write(savePath + File.separator + fileName);
            
        }
 
        request.setAttribute("message", "Upload has been done successfully!");
        getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
        
        // Das hier wird ausgeführt und erst anschliessend geforwarded. Im Browser dreht sich das Ikönchen.
        System.out.println(savedFileName);
        
        Config config = new Config();
        config.setDbdatabase("xanadu2");
        config.setDbhost("localhost");
        config.setDbport("5432");
        config.setDbusr("stefan");
        config.setDbpwd("ziegler12");
        config.setDbschema("test5");
        config.setModels("DM01AVCH24D");
        config.setModeldir("http://models.geo.admin.ch");

        config.setGeometryConverter(PostgisGeometryConverter.class.getName());
        config.setDdlGenerator(GeneratorPostgresql.class.getName());
        config.setJdbcDriver("org.postgresql.Driver");

        config.setNameOptimization("topic");
        config.setMaxSqlNameLength("60");

        config.setDefaultSrsAuthority("EPSG");
        config.setDefaultSrsCode("21781");
        
        config.setXtffile(savedFileName);
        
        String dburl = "jdbc:postgresql://" + config.getDbhost() + ":" + config.getDbport() + "/" + config.getDbdatabase();
        config.setDburl(dburl);
              
        try {
            Ili2db.runImport(config, "");
        } catch (Ili2dbException e) {
            e.printStackTrace();
        }


	}

    /**
     * Extracts file name from HTTP header content-disposition
     */
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }
}
