# BelatrixTest
Belatrix android test

# 1. Propón los pasos, clases, layouts y recursos que utilizarías para hacer un Stepper reutilizable y que cumpla con los parámetros definidos en la guía de material design: https://material.io/guidelines/components/steppers.html

1. Crear un contenedor raíz tipo LinearLayout el cual contendrá 3 secciones principales:

#### Steps: 
Dentro de una vista personalizada, que llamaremos Contenedor de Tabs, se ubicara cada tab que actuara como un 
"Step" del Stepper.

#### Contenido: 
Un Framelayout en el cual residirá un ViewPager, el cual contendrá la vista a mostrar en cada paso, 
está vista puede ser datos a mostrar, acciones a realizar, etc. Hay que recordar que el ViewPager 
responderá a la selección del "Step" en el contenedor de tabs o a los botones Anterior, Continuar o Finalizar.

#### Navegación: 
Un RelativeLayout que contiene los botones "Anterior", "Siguiente" y "Finalizar".

2. Crear la vista personalizada del Contenedor de Tabs, que tendrá dentro un HorizontalScrollView 
sea que la cantidad de steps supere la capacidad de la pantalla y se necesite deslizar el control para 
navegar entre cada Tab, hay que recordar que cada Tab representa a un step y es necesario tener una vista completa 
de estos steps y su descripción. Para crear esta vista definí lo siguiente: 

 #### 2.1 
 El layout del tab o step, el cual contiene el "stepper circle" preparado para mostrarse activo o inactivo de tamaño 24dpx24dp, 
 sea cambiando de color de background o de imagen de fondo, con un número del step actual encima del tipo TextView 
 de Font Roboto Regular con tamaño 12sp. 
 
 A lo anterior con una separación de 8dp se suma el título y subtitulo los cuales serán TextView dentro de un linear layout 
 de color black al 87% con font del tipo Roboto Medium de tamaño 14sp. 
 La altura de este layout debe ser de 72dp con un padding de 24dp para top, bottom y left. 
 
 #### 2.2 
 Crear el view model o clase que contendrá la información de cada step, el cual nos indicará: 
 Titulo, Subtitulo y si el paso es opcional u obligatorio.
 
 #### 2.3 
 La clase que contendrá la vista personalizada, que se encargará de crear los steps en base a una lista de objetos 
 o viewmodel de cada step. Se encargará de actualizarlos de activo a inactivo, también de manejar los Listener 
 para actualizar el ViewPager de contenido y que también moverá el HorizontalScrollView para mostrar en primer 
 lugar el Step actual.

# 2. Crea un shake action en android y pon el código. Proyecto subido a este repo y codígo de referencia aqui: https://github.com/juanchosandox90/BelatrixTest/blob/master/app/src/main/java/app/sandoval/com/belatrixtest/Fragment/FragmentShaker.java

# 3. Explica cómo organizas en base a tu experiencia un proyecto en Android utilizando MVP e implementando Clean Architecture, menciona los paquetes que utilizarías y la distribución de módulos.

1. Primero para comenzar un proyecto con el modelo MVP es necesario agrupar por paquetes, los cuales son nombrados tomando 
como referencia el nombre de los casos de uso.

2. Si por ejemplo vamos a trabajar con "Fragments" estos pertenecen a la capa vista y no manejan logica de negocio, ni llamadas
a servicios, se mantienen lo mas limpios posible. Es decir tiene que ser dependiente de la informacion y operaciones que realize 
la capa Presenter. 

3. Debe establecerse la capa Presenter en base a contratos o especificaciones (interfaces) que describen las validaciones y operaciones 
que seran controladas a traves de esta capa, en esta misma se hace una especie de orquestacion de las funcionalidades donde se controla 
el inicio y fin, pero no debe realizarse logica de negocio, ni llamadas a ejecuciones en segundo plano y mucho menos operaciones de networking.

4. Implementando Arquitectura Limpia (Clean Architecture) la capa Model estara dividida en 
 * La capa de datos donde se establece la persistencia ya sea a una base de datos local tipo SQLite, Realm o uso de SharedPreferences, así como tambien 
   uso de servicios Web del tipo REST u otros usando Retrofit, Volley u otros para la persistencia en la nube. Ademas se puede aplicar el Repository Pattern 
   el cual permite persistir localmente cuando se cuenta con una pobre o inexistente conexion a Internet o se usa una CACHE para eliminar la latencia tanto en 
   el consumo de informacion como en la persistencia de datos. Si se usa el Repository Pattern la persistencia local es mas sencilla usando Room Library.
   
 * La capa de dominio donde se hace uso de Interactors o Use Cases, en la cual se ejecuta la logica de negocio y se controla la persistencia u obtencion de datos, 
   asi como tambien se pueden disparar estas operaciones en segundo plano. Gracias a esta capa se hace una diferenciacion y se añade una capa mas de abstraccion 
   que permite desligar el modelo de datos de las operaciones. Esto permitira al Interactor realizar sus tareas sin responsabilizarse de la capa de datos.

# 4. Diseña un custom view de una brújula utilizando canvas y pon el código que utilizarías en esta sección. Proyecto subido a este repo y codígo de referencia aqui: https://github.com/juanchosandox90/BelatrixTest/blob/master/app/src/main/java/app/sandoval/com/belatrixtest/View/CompassView.java



