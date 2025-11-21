{
  inputs.nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
  inputs.flake-utils.url = "github:numtide/flake-utils";

  outputs = { self, nixpkgs, flake-utils }:
    flake-utils.lib.eachDefaultSystem (system:
      let
        pkgs = nixpkgs.legacyPackages.${system};
      in with pkgs; {
        devShells.default = pkgs.mkShell {
          buildInputs = [ nodejs_24 ];

          packages = [
            direnv
            just
            temurin-bin-25
            tokei
          ];

          shellHook = ''
            # health checks for Nix flake inputs
            nix run "github:DeterminateSystems/flake-checker/v0.2.8"

            npm install @mermaid-js/mermaid-cli
            export PATH=$(pwd)/node_modules/.bin:$PATH
          '';
        };
      }
    );
}
