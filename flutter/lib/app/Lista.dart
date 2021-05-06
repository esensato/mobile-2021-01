import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:teste_flutter/app/Veiculo.dart';

class Lista extends StatefulWidget {

  Lista() {
    print("Construtor LIsta 2");
  }
  @override
  State<Lista> createState() {
    print("create State");
    return _OutroListaState();
  }

}

class _ListaState extends State<Lista> {

  var veiculos = List<Veiculo>.empty(growable: true);
  var itensLista = List<ListTile>.empty(growable: true);

  _ListaState():super() {
    print("_ListaState construtor");
  }

  @override
  void initState() {
    super.initState();
    print("INIT STATE!!!!");
    obterMarcas();
  }

  @override
  Widget build(BuildContext context) {
    print("Build...");

    veiculos.forEach((veiculo) {

      itensLista.add(ListTile(leading: Icon(Icons.car_rental), title: Text(veiculo.marca)));

    });

      return ListView(
        children: itensLista
      );
  }

  void obterMarcas() {
    print("Executando obterMarcas");
    http.get(Uri.https("fipeapi.appspot.com", "api/1/carros/marcas.json")).then((value) {
      var tmp = List<Veiculo>.empty(growable: true);
      var json = jsonDecode(value.body);
      print(json[0]["name"]);

      json.forEach((item) {
        print(item["name"]);
        tmp.add(Veiculo(marca: item["name"], modelo: "", ano: ""));
      });

      setState(() {
        print ("Altera State");
        veiculos = [...tmp];
      });
    });
  }

}

class _OutroListaState extends State<Lista> {

  List<String> listaVeiculos;

  _OutroListaState():super() {
    print("_OutroListaState -> construtor");
  }

  @override
  void initState() {
    super.initState();
    listaVeiculos = List<String>.empty(growable: true);
    listaVeiculos.add("Ford");
    http.get(Uri.https("fipeapi.appspot.com", "api/1/carros/marcas.json")).then((value) {
      var ret = jsonDecode(value.body);
      ret.forEach((item) {

        setState(() {
          listaVeiculos.add(item["name"]);
        });

      });

    });
    print("_OutroListaState -> initState");
  }


  @override
  Widget build(BuildContext context) {

      print("_OutroListaState -> build");

      var itens = List<ListTile>.empty(growable: true);

        listaVeiculos.forEach((item) {
          itens.add(ListTile(leading: Icon(Icons.access_alarm), title: Text(item), onTap: () {
            print(item);
          }));
        });


      return ListView(children: itens);
  }

}