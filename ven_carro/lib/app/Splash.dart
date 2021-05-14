import 'package:flutter/material.dart';
import 'package:splashscreen/splashscreen.dart';
import 'package:ven_carro/app/ListaVeiculo.dart';

class Splash extends StatelessWidget {

  @override
  Widget build(BuildContext context) {

    return Scaffold(
      body: SplashScreen(
          seconds: 5,
          backgroundColor: Colors.white,
          title: Text("VenCarro", style: TextStyle(color: Colors.blue, fontSize: 30)),
          image: Image.network('https://cdn.pixabay.com/photo/2012/04/12/19/39/volga-30332_1280.png'),
          photoSize: 100.0,
          navigateAfterSeconds: ListaVeiculo()
      )
    );

  }

}