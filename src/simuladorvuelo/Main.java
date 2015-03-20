/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simuladorvuelo;

import java.util.ArrayList;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import org.lwjgl.LWJGLException;
//import org.lwjgl.input.Keyboard;
//import org.lwjgl.opengl.Display;
//import org.lwjgl.opengl.DisplayMode;
//import org.lwjgl.input.Keyboard;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import static org.lwjgl.glfw.Callbacks.errorCallbackPrint;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import org.lwjgl.opengl.GLContext;
import static org.lwjgl.system.MemoryUtil.NULL;


/**
 *
 * @author dani
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    
    // We need to strongly reference callback instances.
    private static GLFWErrorCallback errorCallback;
    private static GLFWKeyCallback keyCallback;

    // The window handle
    private static long window;
    private static Simulator simulador;

    
    public static void main(String[] args) 
    {
        initSimulator();
        initDisplay();
        initGL();
        simulatorloop();
        clean();
    }
    
    private static void initSimulator()
    {
        simulador = new Simulator();
    }
    
    private static void initDisplay()
    {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (glfwInit() != GL_TRUE) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure our window
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable

        int WIDTH = 500;
        int HEIGHT = 500;

        // Create the window
        window = glfwCreateWindow(WIDTH, HEIGHT, "Tutorial 3", NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                    glfwSetWindowShouldClose(window, GL_TRUE); // We will detect this in our rendering loop
                }
            }
        });

        // Get the resolution of the primary monitor
        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center our window
        glfwSetWindowPos(
                window,
                (GLFWvidmode.width(vidmode) - WIDTH) / 2,
                (GLFWvidmode.height(vidmode) - HEIGHT) / 2
        );

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }
    
    static final String VertexShaderSrc = 
        "attribute vec3 aVertexPosition;\n" +
        "        void main(void) {\n" +
        "            gl_Position = vec4(aVertexPosition, 1.0);\n" +
        "        }";
    
    static final String FragmentShaderSrc = 
        "        void main(void) {\n" +
        "            gl_FragColor = vec4(1.0, 1.0, 0.0, 1.0);\n" +
        "        }";
    
    private static void initGL()
    {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the ContextCapabilities instance and makes the OpenGL
        // bindings available for use.
        GLContext.createFromCurrent();

        // Set the clear color
        glClearColor(1.0f, 1.0f, 1.0f, 0.0f);


        /// Do not forget to do vertices.flip()! This is important, because passing the buffer without 
        // flipping will crash your JVM because of a EXCEPTION_ACCESS_VIOLATION.

        //declara los datos
        FloatBuffer vertices = BufferUtils.createFloatBuffer(3 * 6);
            vertices.put(-0.6f).put(-0.4f).put(0f).put(1f).put(0f).put(0f);
            vertices.put(0.6f).put(-0.4f).put(0f).put(0f).put(1f).put(0f);
            vertices.put(0f).put(0.6f).put(0f).put(0f).put(0f).put(1f);
            vertices.flip();
        //Crea un buffer
        int vbo = glGenBuffers();
        //Conecto al buffer
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        //Mete los datos declarados en java en el buffer
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        //*************** PREPARAR TOL TEMA DE SHADER *************************

        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShader, VertexShaderSrc);
        glCompileShader(vertexShader);
        int status = glGetShaderi(vertexShader, GL_COMPILE_STATUS);
        if (status != GL_TRUE) {
            throw new RuntimeException(glGetShaderInfoLog(vertexShader));
        }

        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShader, FragmentShaderSrc);
        glCompileShader(fragmentShader);
        status = glGetShaderi(fragmentShader, GL_COMPILE_STATUS);
        if (status != GL_TRUE) {
            throw new RuntimeException(glGetShaderInfoLog(vertexShader));
        }

        int shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexShader);
        glAttachShader(shaderProgram, fragmentShader);
        glBindFragDataLocation(shaderProgram, 0, "fragColor");
        glLinkProgram(shaderProgram);
        glUseProgram(shaderProgram);

        //*******************************************************************

        int floatSize = 4;
        int posAttrib = glGetAttribLocation(shaderProgram, "aVertexPosition");
        glEnableVertexAttribArray(posAttrib);
        glVertexAttribPointer(posAttrib, 3, GL_FLOAT, false, 6 * floatSize, 0);
    }
 
    private static void getinput()
    {
        System.out.println("Recojo Input del simulador");
    }
    
    private static void render()
    {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the ContextCapabilities instance and makes the OpenGL
        // bindings available for use.
        GLContext.createFromCurrent();

        // Set the clear color
        glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
        
        simulador.render();
    }
    
    private static void simulatorloop()
    {
        while (glfwWindowShouldClose(window) == GL_FALSE)
        {
            getinput();
            render();
        }
    }
    
    private static void clean()
    {
        
    }
}
