import 'package:flutter/material.dart';

class Dado extends StatefulWidget {

  @override
  State<Dado> createState() {
    return _DadoState();
  }

}

class _DadoState extends State<Dado> {
  @override
  Widget build(BuildContext context) {


    return Scaffold(
        appBar: AppBar(title: Text("Jogo de Dado")),
        body: Center(child: Image.asset('img/dado0.png'))
    );
  }
}