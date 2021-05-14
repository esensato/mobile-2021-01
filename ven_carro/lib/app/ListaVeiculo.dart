import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

import 'package:ven_carro/app/Veiculo.dart';

final URL_MARCA = 'http://fipeapi.appspot.com/api/1/carros/marcas.json';

class ListaVeiculo extends StatefulWidget {

  @override
  ListaState createState() => ListaState();

}

class ListaState extends State<ListaVeiculo> {

  // uma lista vazia que podemos adicionar novos elementos
  var listaVeiculos = List<Veiculo>.empty(growable: true);

  // veiculo selecionado
  Veiculo selecionado;

  @override
  void initState() {
    super.initState();

    http.get(Uri.parse(URL_MARCA)).then((retorno) {
      print(retorno.body);
      final marcas = jsonDecode(retorno.body);
      print(marcas);
      marcas.forEach((item) {
        print(item["fipe_name"]);

        // atualizar a interface de usuário
        setState(() {
          listaVeiculos.add(Veiculo(idMarca: item["id"], marca: item["fipe_name"]));
        });

      });

    });

  }

  @override
  Widget build(BuildContext context) {

    return Scaffold(
            appBar: AppBar(title: Text('VenCarro')),
            body: Center(child: ListView.builder(
                                  itemBuilder: (context, idx) {

                                    var cor = null;
                                    if (idx % 2 == 0) {
                                      cor = Colors.white;
                                    } else {
                                      cor = Colors.lime;
                                    }

                                    return ListTile(leading: Icon(Icons.directions_car),
                                                    trailing: Icon(Icons.navigate_next),
                                                    title: LinhaLista(veiculo: listaVeiculos[idx]),
                                                    tileColor: cor,
                                                    onTap: () {
                                                      print(listaVeiculos[idx]);
                                                      selecionado = listaVeiculos[idx];

                                                      // efetua requisição para modelo
                                                      if (selecionado.modelo == '') {

                                                        final URL_MODELO = 'http://fipeapi.appspot.com/api/1/carros/veiculos/${selecionado.idMarca}.json';

                                                        listaVeiculos.clear();
                                                        http.get(Uri.parse(URL_MODELO)).then((retorno) {
                                                          final modelos = jsonDecode(retorno.body);
                                                          print(modelos);
                                                          modelos.forEach((item) {
                                                            print(item["fipe_name"]);

                                                            // atualizar a interface de usuário
                                                            setState(() {
                                                              listaVeiculos.add(Veiculo(idMarca: selecionado.idMarca, marca: selecionado.marca,
                                                                                        idModelo: item["id"], modelo: item["fipe_name"]));
                                                            });

                                                          });

                                                        });
                                                        // efetua a requisição para ano (marca / modelo / ano)
                                                      } else if (selecionado.ano == '') {

                                                        final URL_ANO = 'http://fipeapi.appspot.com/api/1/carros/veiculo/${selecionado.idMarca}/${selecionado.idModelo}.json';
                                                        listaVeiculos.clear();

                                                        http.get(Uri.parse(URL_ANO)).then((retorno) {
                                                          final anos = jsonDecode(retorno.body);
                                                          print(anos);
                                                          anos.forEach((item) {
                                                            print(item["name"]);

                                                            // atualizar a interface de usuário
                                                            setState(() {
                                                              listaVeiculos.add(Veiculo(idMarca: selecionado.idMarca, marca: selecionado.marca,
                                                                  idModelo: selecionado.idModelo, modelo: selecionado.modelo,
                                                                  idAno: item["fipe_codigo"], ano: item["name"]));
                                                            });

                                                          });

                                                        });
                                                        // efetua a requisição para obter o preço
                                                      } else if (selecionado.preco == '') {

                                                        final URL_PRECO = 'http://fipeapi.appspot.com/api/1/carros/veiculo/${selecionado.idMarca}/${selecionado.idModelo}/${selecionado.idAno}.json';
                                                        listaVeiculos.clear();

                                                        http.get(Uri.parse(URL_PRECO)).then((retorno) {
                                                          final preco = jsonDecode(retorno.body);
                                                          print(preco["preco"]);

                                                          listaVeiculos.add(Veiculo(idMarca: selecionado.idMarca, marca: selecionado.marca,
                                                              idModelo: selecionado.idModelo, modelo: selecionado.modelo,
                                                              idAno: selecionado.idAno, ano: selecionado.ano,
                                                              preco: preco["preco"]));

                                                        });

                                                      }

                                                    }

                                                    );
                                  },
                                  itemCount: listaVeiculos.length)
                        )
    );
  }

}

class LinhaLista extends StatelessWidget {

  Veiculo veiculo;
  var itens = <Text>[];

  LinhaLista({this.veiculo});

  @override
  Widget build(BuildContext context) {

    print(veiculo.marca);
    print(veiculo.modelo);

    if (veiculo.marca != "") {
      itens.add(Text(veiculo.marca));
    }
    if (veiculo.modelo != "") {
      itens.add(Text(veiculo.modelo));
    }
    if (veiculo.ano != "") {
      itens.add(Text(veiculo.ano));
    }
    
    return Container(padding: EdgeInsets.all(5.0),
        child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: itens));

  }

}