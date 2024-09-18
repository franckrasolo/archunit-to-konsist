{
  inputs.nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
  inputs.flake-utils.url = "github:numtide/flake-utils";

  outputs = { self, nixpkgs, flake-utils }:
    flake-utils.lib.eachDefaultSystem (system:
      let
        pkgs = nixpkgs.legacyPackages.${system};
      in with pkgs; {
        devShells.default = pkgs.mkShell {
          buildInputs = [ nodejs_20 ];

          packages = [
            direnv
            just
            temurin-bin-21
          ];

          shellHook = ''
            # health checks for Nix flake inputs
            nix run "github:DeterminateSystems/flake-checker"

            npm install @mermaid-js/mermaid-cli
            export PATH=$(pwd)/node_modules/.bin:$PATH
          '';
        };
      }
    );
}
